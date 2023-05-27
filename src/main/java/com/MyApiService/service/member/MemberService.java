package com.MyApiService.service.member;

import com.MyApiService.dto.member.MemberRequestDto ;
import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.entity.member.MemberEntity;


/**
 * MemberService 인터페이스는 회원 관리를 위한 서비스의 기능을 정의
 * 회원 생성, 현재 사용자 조회, 사용자 인증 등의 기능을 제공
 */
public interface MemberService {
    
    /**
     * 주어진 회원 정보를 바탕으로 회원을 생성하는 메서드
     * @param memberFormDto 회원 생성에 필요한 정보를 담고 있는 MemberRequestDto 객체
     * @return 생성된 회원의 정보를 담고 있는 MemberResponseDto 객체
     */
    MemberResponseDto createUser(MemberRequestDto memberFormDto);
    
    /**
     * 주어진 이메일과 비밀번호를 바탕으로 사용자를 인증하는 메서드
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 인증된 사용자의 JWT 토큰
     */
    String authenticateUser(String email, String password);
}