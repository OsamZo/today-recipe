package com.goruna.spring.book.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class BookListReadResDTO {

    private String shopName;

    private String productName;

    private int bookQty;

    private LocalTime shopClosedAt;

    private String shopAddress;

    private String shopImgUrl;

    private YnType isBookCancelled;
}
