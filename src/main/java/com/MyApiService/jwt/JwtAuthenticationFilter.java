package com.MyApiService.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.MyApiService.service.member.MemberDetailServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 기반 인증 필터인 JwtAuthenticationFilter 클래스입니다.
 * OncePerRequestFilter를 상속하여 한 번의 요청당 한 번의 필터링을 수행합니다.
 * JWT 토큰을 처리하고 사용자 인증을 수행합니다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider; // JWT 토큰 관리를 담당하는 클래스
    private MemberDetailServiceImpl memberDetailsService; // 사용자 정보를 로드하는 서비스
    private AuthenticationManager authenticationManager; // 인증 관리자
    private String filterProcessesUrl = "/login/action";

    /**
     * JwtAuthenticationFilter의 생성자입니다.
     * 인증 관리자, JWT 토큰 관리자, 사용자 상세 서비스를 주입받습니다.
     * @param authenticationManager 인증 관리자
     * @param tokenProvider JWT 토큰 관리자
     * @param memberDetailsService 사용자 상세 서비스
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, MemberDetailServiceImpl memberDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.memberDetailsService = memberDetailsService;
    }

    /**
     * 필터 처리 URL을 설정하는 메서드입니다.
     * @param filterProcessesUrl 필터 처리 URL
     */
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 필터가 초기화된 후 실행되는 메서드입니다.
     * 필터 처리 URL을 설정합니다.
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        setFilterProcessesUrl(filterProcessesUrl);
    }

    /**
     * 실제 필터 처리 로직을 담고 있는 메서드입니다.
     * JWT 토큰을 확인하고 사용자 인증을 수행합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 JWT 토큰 가져오기
            String jwt = getJwtFromRequest(request);

            // JWT 토큰이 유효한 경우
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                // JWT 토큰으로부터 사용자 이름 가져오기
                String username = tokenProvider.getUsernameFromJWT(jwt);
                // 사용자 정보를 로드
                UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
                // 인증 정보 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 인증 정보를 SecurityContext에 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // 예외 처리
            System.out.println(e);
        }

        // 필터 체인에 요청과 응답 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 요청에서 JWT 토큰을 가져오는 메서드입니다.
     * "Authorization" 헤더에서 토큰을 가져옵니다.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // "Authorization" 헤더에서 토큰 가져오기
        String bearerToken = request.getHeader("Authorization");
        // 토큰이 존재하고 "Bearer "로 시작하는 경우
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // 실제 JWT 토큰만 반환
            return bearerToken.substring(7);
        }
        return null;
    }
}