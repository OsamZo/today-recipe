package com.goruna.spring.book.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "book_receipt_status", nullable = false)
    private boolean bookReceiptStatus = false;

    @Column(name = "product_original_price", nullable = false)
    private int productOriginalPrice;

    @Column(name = "product_sale_price", nullable = false)
    private int productSalePrice;

    @Column(name = "book_status", nullable = false)
    private boolean bookStatus;

    public Book(Shop shop, User user, int bookQty) {
        this.shop = shop;
        this.user = user;
        this.bookQty = bookQty;
    }

    public void updateProductOriginalPrice(int productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public void updateProductSalePrice(int productSalePrice) {
        this.productSalePrice = productSalePrice;
    }
}
