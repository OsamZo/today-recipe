package com.goruna.spring.users.repository;

import com.goruna.spring.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUserEmail(String userEmail);

}
