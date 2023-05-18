package com.MyApiService.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import com.board.entity.member.MemberRepository;
import com.board.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        memberRepository.updateMemberLastLogin(authentication.getName(), LocalDateTime.now());

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(authentication.getName());

        response.addHeader("Authorization", "Bearer " + token);

        setDefaultTargetUrl("/board/list");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
