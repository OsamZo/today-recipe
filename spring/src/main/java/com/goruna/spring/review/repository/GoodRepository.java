package com.goruna.spring.review.repository;

import com.goruna.spring.review.entity.Good;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Optional<Good> findByUserAndReview(User user, Review review);
}
