package com.goruna.spring.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkReadResDTO {

    /* 매장 사진, 매장 이름, 매장 카테고리 이름, 마감 시간, 매장 주소 */
    private String shopImgUrl; // 매장 사진
    private String shopName; // 매장 이름
    private String categoryName; // 매장 카테고리 이름
    private String shopTel;
    private String shopAddress; // 매장 주소
}
