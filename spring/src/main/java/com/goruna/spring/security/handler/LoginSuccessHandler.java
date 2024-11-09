package com.goruna.spring.security.handler;

import com.goruna.spring.security.util.CustomUserDetails;
import com.goruna.spring.security.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String loginEmail = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmail);

        Long userSeq = ((CustomUserDetails) userDetails).getUserSeq();
        String accessToken = jwtUtil.generateToken(userSeq, loginEmail);
        String refreshToken = jwtUtil.generateRefreshToken(userSeq, loginEmail);

        // 액세스 토큰은 일반적으로 응답 헤더나 쿠키로 반환
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 15); // 예: 15분

        // 리프레시 토큰 쿠키 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 예: 7일

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("login success");
    }
}
