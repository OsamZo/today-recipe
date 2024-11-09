package com.goruna.spring.review.entity;

import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "good")
@NoArgsConstructor
@Getter
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goodSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_seq", nullable = false)
    private Review review;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private YnType isClicked = YnType.Y;

    @Builder
    public Good(User user, Review review){
        this.user = user;
        this.review = review;
    }

}
