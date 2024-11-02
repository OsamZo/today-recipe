package com.goruna.spring.users.entity;

import com.goruna.spring.common.aggregate.UserRole;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_seq", nullable = false)
    private long userSeq;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_status", nullable = false)
    private String userStatus = "active";

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole = UserRole.USER;

    @Column(name = "user_deleted_time", nullable = true)
    private LocalDateTime userDeletedTime;

    @Column(name = "user_saved_money", nullable = false)
    private int userSavedMoney;

    @Column(name = "user_usage_count", nullable = false)
    private int userUsageCount = 0;

}
