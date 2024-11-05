package com.goruna.spring.bookmark.entity;

import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookmark")
@NoArgsConstructor
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookmarkSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_seq", nullable = false)
    private Shop shop;

    @Builder
    public Bookmark(User user, Shop shop) {
        this.user = user;
        this.shop = shop;
    }
}
