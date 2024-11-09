package com.goruna.spring.review.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodResDTO {

    private Long goodSeq;
    private YnType isClicked;
}
