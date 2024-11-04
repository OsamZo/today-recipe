package com.goruna.spring.shop.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShopApplyReqDTO {
    /* 사업자 등록증, 상호명, 개업일, 매장 전화번호,
     사업장 주소, 사업장 종류, 사업장 소개*/
    private Long categorySeq;
    private String shopName;
    private String shopBusinessImgUrl;
    private LocalDateTime shopOpenDate;
    private String shopTel;
    private String shopAddress;
    private String shopIntroduction;
    private YnType shopApprStatus;
}
