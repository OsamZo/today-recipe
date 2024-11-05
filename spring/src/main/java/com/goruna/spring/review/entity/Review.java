package com.goruna.spring.review.entity;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewSeq;

    @Column(nullable = false)
    private String reviewContent;

    @Enumerated(EnumType.STRING)
    private YnType reviewStatus = YnType.Y;

    private LocalDateTime delDate;

    @OneToOne
    @JoinColumn(referencedColumnName = "book_seq", nullable = false)
    private Book book;

    @OneToMany(mappedBy = "reviewSeq")
    private List<Good> goods;

    public void Book(Book book) {
        this.book = book;
    }
}
