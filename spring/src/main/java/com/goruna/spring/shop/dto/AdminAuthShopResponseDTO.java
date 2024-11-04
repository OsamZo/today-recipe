package com.goruna.spring.shop.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthShopResponseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shopSeq;
    private String shopName;
    private String categorySeq;
    private String categoryName;
    private String shopTel;
    private String shopAddress;
    private String shopApprStatus;
    private String shopImgUrl;
}
