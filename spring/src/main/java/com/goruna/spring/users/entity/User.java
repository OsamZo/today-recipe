package com.goruna.spring.users.entity;

import com.goruna.spring.bookmark.entity.Bookmark;
import com.goruna.spring.common.aggregate.UserRole;
import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.review.entity.Good;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@SQLDelete(sql="UPDATE users SET user_deleted_status='Y' WHERE user_seq=?")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_seq", nullable = false)
    private long userSeq;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_nickname", nullable = true)
    private String userNickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_deleted_status", nullable = false)
    private YnType userDeletedStatus = YnType.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 10)
    private UserRole userRole = UserRole.USER;

    @Column(name = "user_deleted_time", nullable = true)
    private LocalDateTime userDeletedTime;

    @Column(name = "user_saved_money", nullable = true)
    private int userSavedMoney = 0;

    @Column(name = "user_usage_count", nullable = true)
    private int userUsageCount = 0;

    @OneToMany(mappedBy = "user")
    private List<Good> goods;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks;

    public void UserEmail(String email) {
        this.userEmail = email;
    }

    @Builder
    public static User createUser(long userSeq, String userEmail, String userNickname, UserRole userRole, int userSavedMoney) {
        User user = new User();
        user.userSeq = userSeq;
        user.userEmail = userEmail;
        user.userNickname = userNickname;
        user.userRole = userRole;
        user.userSavedMoney = userSavedMoney;
        return user;
    }
}
