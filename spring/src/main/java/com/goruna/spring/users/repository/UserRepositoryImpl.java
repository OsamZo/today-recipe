package com.goruna.spring.users.repository;

import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.goruna.spring.users.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User getUserInfo(Long currentUserSeq) {

        return jpaQueryFactory
                .selectFrom(user)
                .where(user.userSeq.eq(currentUserSeq))
                .fetchOne();
    }

    @Override
    public User getUserNickName(Long currentUserSeq, UserInfoRequestDto userInfoRequestDto) {

        jpaQueryFactory.update(user)
                .where(user.userSeq.eq(currentUserSeq))
                .set(user.userNickname, userInfoRequestDto.getUserNickname())
                .execute();

        return jpaQueryFactory.selectFrom(user)
                .where(user.userSeq.eq(currentUserSeq))
                .fetchOne();
    }
}
