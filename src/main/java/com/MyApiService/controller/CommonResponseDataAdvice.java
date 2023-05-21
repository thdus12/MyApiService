//package com.MyApiService.controller;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.servlet.HttpServletBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpRequest;
//import org.springframework.ui.ModelMap;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.MyApiService.dto.CommonResponseData;
//
//import java.io.IOException;
//
//@ControllerAdvice
//public class CommonResponseDataAdvice {
//  
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @ModelAttribute
//    public void setResponseData(HttpRequest request, HttpServletBean response, 
//                                ModelMap modelMap) throws IOException {
//        // API 공통 응답 객체를 생성하고 값을 설정
//        CommonResponseData responseData = new CommonResponseData();
//        responseData.setStatus("success");
//        responseData.setMessage("응답 성공");
//        // 이곳에서 필요한 로직을 추가해줄 수 있습니다.
//
//        // 응답 객체를 JSON 문자열로 변환
//        String json = objectMapper.writeValueAsString(responseData);
//
//        // ModelMap 객체에 공통 응답 객체 추가
//        modelMap.addAttribute("responseData", json);
//    }
//}
