package com.goruna.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 인증 무효화 및 특정 경로에 대한 접근 허용 설정
        http
                .authorizeRequests(authorize -> authorize
                        .anyRequest().permitAll() // 모든 요청에 대한 인증 비활성화
                )
                .csrf().disable(); // CSRF 보호 비활성화 (필요에 따라 활성화 가능)

        return http.build();
    }
}
