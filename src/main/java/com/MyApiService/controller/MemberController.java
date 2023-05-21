package com.MyApiService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.dto.member.MemberRequestDto ;
import com.MyApiService.service.member.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "인증 관련 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@Operation(summary = "회원가입", description = "회원가입 메서드입니다.")
	@PostMapping("/member/signup")
	public ResponseEntity<?> createUser(MemberRequestDto memberRequestDto) {
		MemberResponseDto memberResponseDto  = memberService.createUser(memberRequestDto);
	    if(memberResponseDto == null){
	        return new ResponseEntity<>("Failed to sign up", HttpStatus.BAD_REQUEST);
	    }else{
	        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
	    }
	}
	
	@Operation(summary = "로그인", description = "로그인 메서드입니다.")
	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody MemberRequestDto memberRequestDto) {
        try {
            String token = memberService.authenticateUser(memberRequestDto.getEmail(), memberRequestDto.getPassword());
            return ResponseEntity.ok().body("Bearer " + token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}
