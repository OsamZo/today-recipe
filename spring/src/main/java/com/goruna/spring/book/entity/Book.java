package com.goruna.spring.book.entity;

import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.product.entity.Product;
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
    @Column(name = "book_seq")
    private Long bookSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_seq", nullable = false)
    private Product product;

    @Column(name = "book_qty", nullable = false)
    private int bookQty;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_is_product_received", nullable = false, length = 10)
    private YnType bookIsProductReceived = YnType.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_book_cancelled", nullable = false, length = 10)
    private YnType isBookCancelled = YnType.N;

    @Builder
    public Book(User user, Product product, int bookQty) {
        this.user = user;
        this.product = product;
        this.bookQty = bookQty;
    }
}
