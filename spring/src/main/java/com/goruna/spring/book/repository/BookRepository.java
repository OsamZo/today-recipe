package com.goruna.spring.book.repository;

import com.goruna.spring.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // shopSeq로 Book 찾기
    List<Book> findByShopShopSeq(Long shopSeq);

    // userSeq로 Book 찾기
    List<Book> findByUserUserSeq(Long shopSeq);
}