package com.goruna.spring.review.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Enumeration;

@Getter
@AllArgsConstructor
public class ReviewUserReadResDTO {

    private Long reviewSeq;
    private String userNickname;
    private String reviewContent;
    private LocalDateTime regDate;
    private long likeCount; // 좋아요 수
    private YnType reviewStatus;
}
