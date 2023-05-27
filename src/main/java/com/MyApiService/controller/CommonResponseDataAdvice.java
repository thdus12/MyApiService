package com.MyApiService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.MyApiService.dto.CommonResponseData;

/**
 * 공통 응답 데이터를 생성하기 위한 컨트롤러 어드바이스 클래스
 */
@ControllerAdvice
public class CommonResponseDataAdvice {

    /**
     * 실패 응답 데이터를 생성하는 메서드
     * @param status HTTP 상태 코드
     * @param message 응답 메시지
     * @return ResponseEntity 객체에 실패 응답 데이터를 담아 반환
     */
    public ResponseEntity<CommonResponseData> createFailureResponse(HttpStatus status, String message) {
        CommonResponseData responseData = new CommonResponseData();
        responseData.setStatus("failure");
        responseData.setMessage(message);

        return ResponseEntity.status(status).body(responseData);
    }

    /**
     * 성공 응답 데이터를 생성하는 메서드
     * @param status HTTP 상태 코드
     * @param message 응답 메시지
     * @return ResponseEntity 객체에 성공 응답 데이터를 담아 반환
     */
    public ResponseEntity<CommonResponseData> createSuccessResponse(HttpStatus status, String message) {
        CommonResponseData responseData = new CommonResponseData();
        responseData.setStatus("success");
        responseData.setMessage(message);

        return ResponseEntity.status(status).body(responseData);
    }
}
