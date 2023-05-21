package com.MyApiService.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    @NotNull
    @Email
    @Schema(description = "User's email address", example = "test@test.com", required = true)
    private String email;
    @NotNull
    @Schema(description = "User's password", example = "1234", required = true)
    private String password;
}