package com.goruna.spring.book.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerShopBooksResDTO {

    private Long bookSeq;
    private String userNickname;
    private String productName;
    private Integer bookQty;
    private Integer totalPrice;
    private YnType bookIsProductReceived;
    private LocalDateTime regDate;

}
