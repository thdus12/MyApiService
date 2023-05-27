package com.MyApiService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.annotation.CommonApiResponses;
import com.MyApiService.dto.member.MemberRequestDto ;
import com.MyApiService.service.member.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

/**
 * 회원 인증과 관련된 API를 처리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final CommonResponseDataAdvice commonResponseDataAdvice;

    /**
     * 회원가입 API
     * @param memberRequestDto 회원가입 요청 데이터
     * @return 회원가입 결과 응답
     */
    @PostMapping("/member/signup")
    @Operation(summary = "회원가입", description = "회원가입 메서드")
    @CommonApiResponses
    public ResponseEntity<?> createUser(@RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto memberResponseDto = memberService.createUser(memberRequestDto);
        if (memberResponseDto == null) {
            return commonResponseDataAdvice.createFailureResponse(HttpStatus.BAD_REQUEST, "회원 가입 실패");
        } else {
            return commonResponseDataAdvice.createSuccessResponse(HttpStatus.CREATED, "회원 가입 성공");
        }
    }

    /**
     * 로그인 API
     * @param memberRequestDto 로그인 요청 데이터
     * @return 로그인 결과 응답
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 메서드")
    @CommonApiResponses
    public ResponseEntity<?> authenticateUser(@RequestBody MemberRequestDto memberRequestDto) {
        try {
            String token = memberService.authenticateUser(memberRequestDto.getEmail(), memberRequestDto.getPassword());
            System.out.println("@@@@token="+token);
            return commonResponseDataAdvice.createSuccessResponse(HttpStatus.OK, "로그인 성공");
        } catch (AuthenticationException e) {
            return commonResponseDataAdvice.createFailureResponse(HttpStatus.BAD_REQUEST, "로그인 실패");
        }
    }
}
