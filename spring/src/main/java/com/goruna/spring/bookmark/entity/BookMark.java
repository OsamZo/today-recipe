package com.goruna.spring.bookmark.entity;

import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookmark")
@NoArgsConstructor
@Getter
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boomarkSeq;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User userSeq;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private Shop shopSeq;
}
