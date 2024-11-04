package com.goruna.spring.book.entity;

import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_seq", nullable = false)
    private Long bookSeq;

    @ManyToOne
    @JoinColumn(name = "shopSeq")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

    @Column(name = "book_qty", nullable = false)
    private int bookQty;

    @Column(name = "book_is_product_received", nullable = false)
    private YnType bookIsProductReceived = YnType.N;

    @Column(name = "product_original_price", nullable = false)
    private int productOriginalPrice;

    @Column(name = "product_sale_price", nullable = false)
    private int productSalePrice;

    @Column(name = "is_book_cancelled", nullable = false)
    private YnType isBookCancelled = YnType.N;

    @Builder
    public Book(Shop shop, User user, int bookQty, int productOriginalPrice, int productSalePrice) {
        this.shop = shop;
        this.user = user;
        this.bookQty = bookQty;
        this.productOriginalPrice = productOriginalPrice;
        this.productSalePrice = productSalePrice;
    }
}
