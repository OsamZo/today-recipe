package com.goruna.spring.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewUserReadResDTO {

    private String userNickname;
    private String reviewContent;
    private LocalDateTime regDate;
    private long likeCount; // 좋아요 수
}
