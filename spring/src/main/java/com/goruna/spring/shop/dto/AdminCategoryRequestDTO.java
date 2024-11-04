package com.goruna.spring.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminCategoryRequestDTO {

    @NotBlank(message = "카테고리 이름은 필수 입력 항목입니다.")
    private String categoryName;

}
