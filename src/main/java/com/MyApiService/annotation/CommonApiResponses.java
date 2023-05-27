package com.MyApiService.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.*;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 공통 API 응답에 대한 Swagger 어노테이션입니다.
 * API 응답의 다양한 상태에 대한 예시 및 설명을 포함합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "요청 성공",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"200\","
                        + "\"message\":\"API 호출 성공\"}"))),

    @ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 API",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"404\","
                        + "\"message\":\"존재하지 않는 API\"}"))),

    @ApiResponse(
        responseCode = "400",
        description = "유효성 검증 실패",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"400\","
                        + "\"message\":\"유효성 검증 실패\"}"))),

    @ApiResponse(
        responseCode = "405",
        description = "잘못된 Method 요청",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"405\","
                        + "\"message\":\"잘못된 Method 요청\"}"))),

    @ApiResponse(
        responseCode = "401",
        description = "인증 실패",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"401\","
                        + "\"message\":\"인증 실패\"}"))),

    @ApiResponse(
        responseCode = "403",
        description = "인가 실패(권한 없음)",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"403\","
                        + "\"message\":\"인가 실패(권한 없음)\"}"))),

    @ApiResponse(
        responseCode = "408",
        description = "토큰 유효기간 만료",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"408\","
                        + "\"message\":\"토큰 유효기간 만료\"}"))),

    @ApiResponse(
        responseCode = "500",
        description = "데이터 등록 실패",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\"status\":\"500\","
                        + "\"message\":\"데이터 등록 실패\"}"))),
})
public @interface CommonApiResponses {}