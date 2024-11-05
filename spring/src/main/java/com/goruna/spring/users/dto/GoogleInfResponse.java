package com.goruna.spring.users.dto;

import com.goruna.spring.common.aggregate.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    private String userEmail;
    private String userNickname;
}
