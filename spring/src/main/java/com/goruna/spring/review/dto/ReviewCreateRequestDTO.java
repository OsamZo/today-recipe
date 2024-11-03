package com.goruna.spring.review.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewCreateRequestDTO {

    private String reviewContent;
    private YnType reviewStatus;
}
