package com.goruna.spring.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final CustomUserDetailsService userDetailsService;

    public JwtUtil(
            @Value("${spring.token.secret}") String secretKey,
            CustomUserDetailsService userDetailsService
    ){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService  = userDetailsService;
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Token claims empty {}", e);
        }

        return false;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getLoginId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getLoginId(String token) {
        return parseClaims(token).getSubject();
    }

    public Long getUserSeq(String token) {
        Claims claims = parseClaims(token);
        Long userSeq = claims.get("userSeq", Long.class); // userSeq를 클레임에서 가져오기
        return userSeq;
    }

    public String generateToken(Long userSeq, String userEmil) {
        String token = Jwts.builder()
                .setSubject(userEmil)
                .claim("userSeq", userSeq)
                .signWith(key)
                .compact();
        return token;
    }

    public String generateRefreshToken(Long userSeq, String loginEmail) {
        // 만료 기간 설정 (예: 7일)
        long expirationTime = 1000L * 60 * 60 * 24 * 7; // 7일

        String refreshToken = Jwts.builder()
                .setSubject(loginEmail)
                .claim("userSeq", userSeq)
                .signWith(key)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationTime)) // 만료 시간 설정
                .compact();
        return refreshToken;
    }
}
