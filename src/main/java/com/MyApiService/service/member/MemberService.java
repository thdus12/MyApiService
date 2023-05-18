package com.MyApiService.service.member;

import com.MyApiService.dto.member.MemberRequestDto ;
import com.MyApiService.dto.member.MemberResponseDto;
import com.MyApiService.entity.member.MemberEntity;


public interface MemberService {
	MemberResponseDto createUser(MemberRequestDto memberFormDto);	
	MemberEntity getCurrentUser(MemberRequestDto memberFormDto);
}