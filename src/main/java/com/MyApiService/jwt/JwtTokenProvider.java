package com.MyApiService.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtTokenProvider 클래스는 JWT 토큰을 생성하고 검증하는 기능을 제공하는 클래스입니다.
 * Component 어노테이션을 통해 스프링의 빈으로 등록되었습니다.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret; // JWT 비밀 키

    @Value("${jwt.expiration}")
    private long jwtExpiration; // JWT 만료 시간

    /**
     * 주어진 사용자 이름을 기반으로 JWT 토큰을 생성하는 메서드입니다.
     * @param username 사용자 이름
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username) {
        Date now = new Date(); // 현재 시간
        Date expiryDate = new Date(now.getTime() + jwtExpiration); // 만료 시간 계산

        // JWT 토큰 생성
        String token = Jwts.builder()
                .setSubject(username)        // 사용자 이름 설정
                .setIssuedAt(now)            // 발행 시간 설정
                .setExpiration(expiryDate)   // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // 서명 알고리즘 및 비밀 키 설정
                .compact(); // JWT 토큰 반환
        
        return token;
    }

    /**
     * 주어진 JWT 토큰으로부터 사용자 이름을 가져오는 메서드입니다.
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret) // 서명 키 설정
                .parseClaimsJws(token)    // JWT 토큰 파싱
                .getBody();               // 클레임 몸체 가져오기

        return claims.getSubject(); // 사용자 이름 반환
    }

    /**
     * 주어진 JWT 토큰의 유효성을 검사하는 메서드입니다.
     * @param authToken 검사할 JWT 토큰
     * @return 유효한 토큰인 경우 true, 그렇지 않은 경우 false 반환
     */
    public boolean validateToken(String authToken) {
        try {
            // JWT 토큰 파싱 및 검증
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true; // 유효한 토큰인 경우 true 반환
        } catch (Exception e) {
            return false; // 예외 발생 시, 유효하지 않은 토큰으로 간주하고 false 반환
        }
    }
}