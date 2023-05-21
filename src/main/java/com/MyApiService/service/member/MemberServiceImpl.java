package com.MyApiService.service.member;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager; 
    private final JwtTokenProvider jwtTokenProvider; 
    
    @Override
    public MemberResponseDto createUser(MemberRequestDto memberRequestDto) {

        // 이메일 중복 확인
        if(memberRepository.findByEmail(memberRequestDto.getEmail()) != null){
            return null;
        }
        
        log.info("Role: {}", Role.MEMBER);
        
        // 가입한 성공한 모든 유저는  "MEMBER" 권한 부여
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

	@Override
	public MemberEntity getCurrentUser(MemberRequestDto memberRequestDto) {
		 MemberEntity member = memberRepository.findByEmail(memberRequestDto.getEmail());
        return member;
	}
	
	@Override
	public String authenticateUser(String email, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        MemberEntity member = memberRepository.findByEmail(authentication.getName());

        return jwtTokenProvider.generateToken(member.getEmail());
    }

	public boolean isAdmin(String name) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MemberEntity) {
        	MemberEntity memberEntity = (MemberEntity) authentication.getPrincipal();
            return memberEntity.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
	}

	public MemberEntity getMemberByEmail(String memberId) {
		return memberRepository.findByEmail(memberId);
	}
}