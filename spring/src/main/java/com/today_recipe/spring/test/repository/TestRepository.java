package com.today_recipe.spring.test.repository;

import com.today_recipe.spring.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
