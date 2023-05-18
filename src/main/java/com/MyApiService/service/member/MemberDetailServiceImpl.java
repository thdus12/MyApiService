package com.MyApiService.service.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.MyApiService.entity.member.MemberEntity;
import com.MyApiService.entity.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service // 이 클래스를 스프링 서비스로 등록
@RequiredArgsConstructor // 필수 생성자를 자동으로 생성
// UserDetailsService 인터페이스를 구현하여 스프링 시큐리티와 호환
@Slf4j
public class MemberDetailServiceImpl implements UserDetailsService {
	// 멤버 리포지토리를 주입
    private final MemberRepository memberRepository;      
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	log.info("check : " + email);
        // 이메일로 사용자를 조회
        MemberEntity member = memberRepository.findByEmail(email);

        // 사용자가 없으면 UsernameNotFoundException을 던짐
        if (member == null) throw new UsernameNotFoundException("Not Found account.");
        
        // 조회된 멤버 객체를 반환
        return member;
    }
}
