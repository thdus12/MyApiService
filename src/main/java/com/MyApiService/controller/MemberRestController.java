package com.MyApiService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MyApiService.dto.member.MemberRequestDto;
import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.jwt.JwtTokenProvider;
import com.MyApiService.service.member.MemberService;

import lombok.RequiredArgsConstructor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Api(tags = "Member API")
public class MemberRestController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @ApiOperation("Create a new member")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully created a new member"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto memberResponseDto = memberService.createUser(memberRequestDto);
        if (memberResponseDto == null) {
            return ResponseEntity.badRequest().body("회원가입에 실패하였습니다."); // 회원가입 실패 응답
        } else {
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다."); // 회원가입 성공 응답
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // 인증에 성공한 경우 JWT 토큰을 생성하여 응답으로 반환합니다.
        String token = jwtTokenProvider.generateToken(username);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 인증 정보입니다.");
        }
    }
}