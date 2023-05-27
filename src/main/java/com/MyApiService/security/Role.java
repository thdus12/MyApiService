package com.MyApiService.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	MEMBER("ROLE_MEMBER"), // 일반 회원 역할
    ADMIN("ROLE_ADMIN");   // 관리자 역할
    private String role;
}