package com.MyApiService.service.member;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.MyApiService.dto.member.MemberRequestDto;
import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.entity.member.MemberEntity;
import com.MyApiService.entity.member.MemberRepository;
import com.MyApiService.jwt.JwtTokenProvider;
import com.MyApiService.security.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MemberServiceImpl은 MemberService 인터페이스를 구현한 클래스로, 회원 관리 기능을 제공
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager; 
    private final JwtTokenProvider jwtTokenProvider; 
    
    /**
     * 회원 생성 메서드
     * 주어진 회원 정보를 바탕으로 회원을 생성하고, 생성된 회원의 정보를 MemberResponseDto 형태로 반환
     * @param memberRequestDto 생성할 회원의 정보를 담고 있는 MemberRequestDto 객체
     * @return 생성된 회원의 정보를 담고 있는 MemberResponseDto 객체
     */
    @Override
    public MemberResponseDto createUser(MemberRequestDto memberRequestDto) {
        // 이메일 중복 확인
        if(memberRepository.findByEmail(memberRequestDto.getEmail()) != null){
            return null;
        }
        
        log.info("memberRequestDto.getEmail: {}", memberRequestDto.getEmail());
        log.info("memberRequestDto.getPassword: {}", memberRequestDto.getPassword());
        log.info("Role: {}", Role.MEMBER);
        
        // 가입한 성공한 모든 유저는 "MEMBER" 권한을 부여
        MemberEntity member = memberRepository.save(MemberEntity.builder()
                                        .pwd(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()))
                                        .email(memberRequestDto.getEmail())
                                        .role(Role.MEMBER)
                                        .build());
        return MemberResponseDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .password(member.getPassword())
                        .build();
    }
	
	/**
	 * 사용자 인증 메서드
	 * 주어진 이메일과 비밀번호를 바탕으로 사용자를 인증하고, 인증된 사용자의 JWT 토큰을 반환
	 * @param email 사용자 이메일
	 * @param password 사용자 비밀번호
	 * @return 인증된 사용자의 JWT 토큰
	 * @throws AuthenticationException 인증 예외가 발생한 경우
	 */
	@Override
	public String authenticateUser(String email, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        MemberEntity member = memberRepository.findByEmail(authentication.getName());
        
        memberRepository.updateMemberLastLogin(email, LocalDateTime.now());

        return jwtTokenProvider.generateToken(member.getEmail());
    }
}