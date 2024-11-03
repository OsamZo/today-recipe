package com.goruna.spring.review.entity;

import com.goruna.spring.common.aggregate.ReviewStatus;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewSeq;

    @Column(nullable = false)
    private String reviewContent;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus = ReviewStatus.ACTIVE;

    private LocalDateTime delDate;

//    @OneToOne
//    @JoinColumn(referencedColumnName = "book_seq")
//    private Book bookSeq;

}
