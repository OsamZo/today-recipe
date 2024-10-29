package com.today_recipe.spring.test.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 받는 생성자
public class TestRequestDTO {

    private String content;
}
