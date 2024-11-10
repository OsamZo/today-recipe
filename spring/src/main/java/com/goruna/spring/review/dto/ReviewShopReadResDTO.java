package com.goruna.spring.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewShopReadResDTO {

    private String userNickname;
    private LocalDateTime regDate;
    private String reviewContent;
    private int likeCount;
    private Long reviewSeq;
}
