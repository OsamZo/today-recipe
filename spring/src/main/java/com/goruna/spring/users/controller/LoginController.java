package com.goruna.spring.users.controller;

import com.goruna.spring.security.util.JwtUtil;
import com.goruna.spring.users.dto.GoogleInfResponse;
import com.goruna.spring.users.dto.GoogleRequest;
import com.goruna.spring.users.dto.GoogleResponse;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import com.goruna.spring.users.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping(value="/oauth2/google", method = RequestMethod.GET)
    public RedirectView loginGoogle(@RequestParam(value = "code") String authCode){
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

        if ("signup".equals(result)) {
            // 회원가입 후 자동 로그인 위해 JWT 토큰 반환
            User user = userRepository.findByUserEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtUtil.generateToken(user.getUserSeq(), email);
            System.out.println(token);
            // 클라이언트에 JWT 토큰을 전달하는 방식
            return new RedirectView("http://localhost:5173/nickName?token=" + token);  // token을 프론트엔드로 넘김

        } else {
            // 이미 로그인한 사용자일 경우
            return new RedirectView("http://localhost:5173");
        }
    }
}