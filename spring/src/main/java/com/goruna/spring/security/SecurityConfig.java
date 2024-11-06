package com.goruna.spring.security;

import com.goruna.spring.security.dto.JwtAuthenticationEntryPoint;
import com.goruna.spring.security.filter.CustomAuthenticationFilter;
import com.goruna.spring.security.filter.JwtFilter;
import com.goruna.spring.security.handler.JwtAccessDeniedHandler;
import com.goruna.spring.security.handler.LoginSuccessHandler;
import com.goruna.spring.security.util.CustomUserDetailsService;
import com.goruna.spring.security.util.JwtUtil;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors
                        .configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요한 경우)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/join", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/login", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/api/v1/main")  // 성공 시 리디렉션 경로 설정
                        .failureUrl("/login?error=true")
                );
                http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

                http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

                http.exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(new JwtAccessDeniedHandler());
                    exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                });



        return http.build();
    }

    private Filter getAuthenticationFilter() {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(getAuthenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(env,jwtUtil,customUserDetailsService));
        return customAuthenticationFilter;
    }

    private AuthenticationManager getAuthenticationManager() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // 허용할 도메인
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메소드 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
