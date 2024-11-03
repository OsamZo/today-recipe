package com.goruna.spring.book.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@Getter
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_seq", nullable = false)
    private long bookSeq;

    @Column(name = "shop_seq", nullable = false)
    private long shopSeq;

    @Column(name = "user_seq", nullable = false)
    private long userSeq;

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
}
