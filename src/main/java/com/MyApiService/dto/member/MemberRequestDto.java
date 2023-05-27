package com.MyApiService.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 가입 및 로그인 API에서 사용되는 회원 정보 요청 DTO 클래스
 */
@Getter
@Setter
public class MemberRequestDto {

    @NotNull
    @Email
    @Schema(description = "사용자 이메일 주소", example = "test@test.com", required = true)
    private String email; // 사용자의 이메일

    @NotNull
    @Schema(description = "사용자 비밀번호", example = "1234", required = true)
    private String password; // 사용자의 비밀번호
}
