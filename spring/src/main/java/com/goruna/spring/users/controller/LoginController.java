package com.goruna.spring.users.controller;

import com.goruna.spring.security.util.JwtUtil;
import com.goruna.spring.users.dto.GoogleInfResponse;
import com.goruna.spring.users.dto.GoogleRequest;
import com.goruna.spring.users.dto.GoogleResponse;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import com.goruna.spring.users.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientPw;

    private final LoginService loginService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @RequestMapping(value="/oauth2/google", method = RequestMethod.POST)
    public String loginUrlGoogle(){
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost:8100/api/v1/oauth2/google&response_type=code&scope=email&access_type=offline";
        return reqUrl;
    }

    @RequestMapping(value = "/oauth2/google", method = RequestMethod.GET)
    public ResponseEntity<Void> loginGoogle(@RequestParam(value = "code") String authCode, HttpServletResponse response) {
        System.out.println("Received auth code: " + authCode);
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri("http://localhost:8100/api/v1/oauth2/google")
                .grantType("authorization_code").build();

        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                googleOAuthRequestParam,
                GoogleResponse.class
        );

        String jwtToken = resultEntity.getBody().getId_token();
        Map<String, String> map = new HashMap<>();
        map.put("id_token", jwtToken);

        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/tokeninfo",
                map,
                GoogleInfResponse.class
        );

        String email = resultEntity2.getBody().getEmail();
        String result = loginService.socialLogin(email);

        User user;
        try {
            // 사용자가 존재하는지 확인
            user = userRepository.findByUserEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            // 사용자 존재하지 않을 경우 새 사용자 등록
            user = new User();
            user.UserEmail(email);
            userRepository.save(user);
            System.out.println("New user registered with email: " + email);
        }

        // JWT 토큰 발급
        String accessToken = jwtUtil.generateToken(user.getUserSeq(), email);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserSeq(), email);

        // 쿠키 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .build();

        ResponseCookie accessTokenCookie = ResponseCookie.from("token", accessToken)
                .httpOnly(true)
                .path("/")
                .build();

        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        if ("signup".equals(result)) {
            return ResponseEntity.status(302).header("Location", "http://localhost:5173/nickName").build();
        } else {
            return ResponseEntity.status(302).header("Location", "http://localhost:5173").build();
        }
    }



    @PostMapping("/auth/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            Long userSeq = jwtUtil.getUserSeq(refreshToken);
            String userEmail = jwtUtil.getLoginId(refreshToken);

            // 사용자 확인
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 새 액세스 토큰 발급
            String newAccessToken = jwtUtil.generateToken(userSeq, userEmail);

            // 응답 데이터 생성
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);

            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}