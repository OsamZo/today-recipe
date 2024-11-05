package com.goruna.spring.book.dto;

import com.goruna.spring.common.aggregate.YnType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookInfoReqDTO {

    private YnType bookIsProductReceived;
}
