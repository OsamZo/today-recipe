package com.goruna.spring.users.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    private String user_email;
    private String user_nickname;
}
