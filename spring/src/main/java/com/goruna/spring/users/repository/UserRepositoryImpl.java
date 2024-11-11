package com.goruna.spring.users.repository;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.product.entity.QProduct.product;
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

    // 회원 이용 내역 조회
    @Override
    public List<Book> readUserHistory(Long userSeq) {
        return jpaQueryFactory
                .selectFrom(book)
                .join(book.product).fetchJoin()
                .join(product.shop).fetchJoin()
                .where(book.user.userSeq.eq(userSeq))
                .where(book.bookIsProductReceived.eq(YnType.Y))
                .fetch();
    }
}
