package com.goruna.spring.book.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OwnerShopBooksResDTO {

    private String userNickname;
    private String productName;
    private Integer bookQty;
    private Integer TotalPrice;
    private YnType bookIsProductReceived;

}
