package com.MyApiService.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 공통 응답 데이터 DTO 클래스입니다.
 */
@Getter
@Setter
public class CommonResponseData {

    private String status; // 상태 정보
    private String message; // 메시지 정보
}