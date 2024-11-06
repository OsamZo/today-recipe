package com.goruna.spring.users.repository;

import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.entity.User;

public interface UserRepositoryCustom {
    User getUserInfo(Long currentUserSeq);

    User getUserNickName(Long currentUserSeq, UserInfoRequestDto userInfoRequestDto);
}
