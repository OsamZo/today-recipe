package com.goruna.spring.review.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminReviewShopResDTO {

    private Long shopSeq;
    private String shopName;
    private String shopImgUrl;
    private Long userSeq;
    private String userNickname;
    private Long reviewSeq;
    private String reviewContent;
    private YnType reviewStatus;


}
