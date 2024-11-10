package com.goruna.spring.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GoodRequestDTO {

    private Long userSeq;
    private Long reviewSeq;
}
