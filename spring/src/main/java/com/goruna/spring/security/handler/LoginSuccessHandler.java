package com.goruna.spring.security.handler;

import com.goruna.spring.security.util.CustomUserDetails;
import com.goruna.spring.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Environment env;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공 후 security가 관리하는 객체 : {}", authentication);

        List<String> authorites = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("auth", authorites);

        String loginEmail = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmail); // UserDetails 가져오기

        // UserDetails에서 UserSeq를 가져옵니다.
        Long userSeq = ((CustomUserDetails) userDetails).getUserSeq();
        String token = jwtUtil.generateToken(userSeq, loginEmail);
        response.setHeader("Authorization", "Bearer " + token);
        response.getWriter().write("로그인 성공: " + token); // 응답 본문에 토큰 추가
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
