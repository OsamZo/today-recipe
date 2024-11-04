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
public class AdminShopResponseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shopSeq;
    private String shopName;
    private String categorySeq;
    private String categoryName;
    private String shopAddress;
    private String shopImgUrl;

}
