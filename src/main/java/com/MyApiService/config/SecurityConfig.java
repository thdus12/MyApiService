package com.MyApiService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.MyApiService.jwt.JwtAuthenticationFilter;
import com.MyApiService.jwt.JwtTokenProvider;
import com.MyApiService.service.member.MemberDetailServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정을 위한 클래스
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailServiceImpl memberDetailServiceImpl;

    /**
     * JwtAuthenticationFilter 빈을 생성하는 메서드
     * JwtAuthenticationFilter는 JWT 인증을 처리하는 필터
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, memberDetailServiceImpl);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login/**");
        return jwtAuthenticationFilter;
    }

    /**
     * BCryptPasswordEncoder 빈을 생성하는 메서드
     * BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManagerBean을 빈으로 등록하는 메서드
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 사용자 인증을 위한 설정을 진행하는 메서드
     * UserDetailsService를 사용하여 사용자 정보를 조회하고, BCryptPasswordEncoder를 사용하여 비밀번호를 비교
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailServiceImpl).passwordEncoder(passwordEncoder());
    }

    /**
     * WebSecurity 설정을 진행하는 메서드
     * Swagger 관련 리소스에 대해서는 인증 절차를 수행하지 않도록 설정
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }

    /**
     * HttpSecurity 설정을 진행하는 메서드
     * 인증, 권한, 세션 관리 등의 설정을 수행
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() 	 // CSRF 토큰 비활성화
            .authorizeRequests() // 요청 URL에 따라 접근 권한 설정
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll() // 이 경로들은 모든 사용자에게 접근 허용
                .anyRequest().authenticated()
            .and()
            .sessionManagement() 	// 세션 관리 설정
                .maximumSessions(1) // 최대 허용 세션 수 (1)
                .maxSessionsPreventsLogin(false) // 동시 로그인 방지 기능 비활성화
                .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired"); // 세션이 만료된 경우 이동할 URL

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter 추가
    }
}