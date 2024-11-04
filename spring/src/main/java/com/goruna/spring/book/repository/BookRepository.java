package com.goruna.spring.book.repository;

import com.goruna.spring.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByShopShopSeq(Long shopSeq);
}
