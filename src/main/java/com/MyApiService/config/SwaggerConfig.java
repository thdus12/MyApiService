package com.MyApiService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

/**
 * Swagger 설정을 위한 클래스
 */
@Configuration
public class SwaggerConfig {

    /**
     * 커스텀 OpenAPI 객체를 생성하는 메서드
     * @return 생성된 OpenAPI 객체
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
            .title("MyApiService") 	 // API 문서의 타이틀
            .version("v0.0.1") 		 // API 문서의 버전
            .description("로그인, 회원가입 API 문서"); // API 문서의 설명

        // Security 스키마 설정
        SecurityScheme bearerAuth = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        return new OpenAPI()
            // Security 인증 컴포넌트 설정
            .components(new Components().addSecuritySchemes("JWT", bearerAuth))
            // API마다 Security 인증 컴포넌트 설정
            .addSecurityItem(addSecurityItem)
            .info(info);
    }
}