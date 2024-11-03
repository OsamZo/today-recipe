package com.goruna.spring.review.entity;

import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "good")
@NoArgsConstructor
@Getter
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goodSeq;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User userSeq;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Review reviewSeq;

}
