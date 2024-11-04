package com.goruna.spring.review.repository;

import com.goruna.spring.review.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
}
