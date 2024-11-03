package com.goruna.spring.users.repository;

import com.goruna.spring.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
