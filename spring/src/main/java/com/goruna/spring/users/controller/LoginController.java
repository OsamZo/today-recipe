package com.goruna.spring.users.controller;

import com.goruna.spring.users.dto.GoogleInfResponse;
import com.goruna.spring.users.dto.GoogleRequest;
import com.goruna.spring.users.dto.GoogleResponse;
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
            return new RedirectView("http://localhost:5173/login");
        } else {
            return new RedirectView("http://localhost:5173/nickName");
        }
    }
}