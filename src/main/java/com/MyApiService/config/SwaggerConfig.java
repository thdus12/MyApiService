package com.MyApiService.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		Info info = new Info()
	            .title("MyApiService") // 타이틀
	            .version("v0.0.1") // 문서 버전
	            .description("API 문서입니다."); // 문서 설명
//	            .contact(new Contact() // 연락처
//	            	.name("BAE SOYEON")
//	                .email("bthdus5890@gmail.com"));
				
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
				// API 마다 Security 인증 컴포넌트 설정
	            .addSecurityItem(addSecurityItem)
	            .info(info);
  	}
}

