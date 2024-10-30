package com.goruna.spring.test.repository;

import com.goruna.spring.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
