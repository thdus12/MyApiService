package com.MyApiService.service.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.MyApiService.entity.member.MemberEntity;
import com.MyApiService.entity.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MemberDetailServiceImpl 클래스는 UserDetailsService 인터페이스를 구현하여 사용자 정보를 로드하는 서비스
 * UserDetailsService를 구현하여 사용자 이름(email)을 기반으로 사용자 정보를 조회
 * Service 어노테이션을 통해 스프링의 빈으로 등록
 */
@Service
@RequiredArgsConstructor 
@Slf4j
public class MemberDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository; // 멤버 리포지토리를 주입

    /**
     * 주어진 이메일을 사용하여 사용자를 조회하는 메소드
     * 
     * @param email 조회할 사용자의 이메일
     * @return 조회된 사용자의 UserDetails 객체
     * @throws UsernameNotFoundException 주어진 이메일에 해당하는 사용자가 없을 경우 발생
     */
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