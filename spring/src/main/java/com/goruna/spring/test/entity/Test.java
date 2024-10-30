package com.goruna.spring.test.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor

// @Setter, @AllArgsConstructor, @ToString 사용 지양
// -> Entity 수정 시 필요한 메서드를 직접 선언하여 사용
public class Test extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

}
