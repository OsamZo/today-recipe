package com.goruna.spring.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private String userNickname;

    private String userEmail;

    private int userSavedMoney;

    private int userUsageCount;
}
