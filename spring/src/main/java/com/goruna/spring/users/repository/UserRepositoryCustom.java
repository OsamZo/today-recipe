package com.goruna.spring.users.repository;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    User getUserInfo(Long currentUserSeq);

    User getUserNickName(Long currentUserSeq, UserInfoRequestDto userInfoRequestDto);

    List<Book> readUserHistory(Long userSeq);
}
