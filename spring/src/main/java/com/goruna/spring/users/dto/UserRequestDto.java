package com.goruna.spring.users.dto;

import com.goruna.spring.common.aggregate.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userEmail;
    private String userNickname;
}
