package com.MyApiService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.MyApiService.dto.CommonResponseData;

@ControllerAdvice
public class CommonResponseDataAdvice {

	public ResponseEntity<CommonResponseData> createFailureResponse(HttpStatus status, String message) {
		CommonResponseData responseData = new CommonResponseData();
		responseData.setStatus("failure");
		responseData.setMessage(message);

		return ResponseEntity.status(status).body(responseData);
	}

	public ResponseEntity<CommonResponseData> createSuccessResponse(HttpStatus status, String message) {
		CommonResponseData responseData = new CommonResponseData();
		responseData.setStatus("success");
		responseData.setMessage(message);

		return ResponseEntity.status(status).body(responseData);
	}
}
