package com.goruna.spring.review.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodCreateResDTO {

    private Long goodSeq;
    private YnType isClicked;
}
