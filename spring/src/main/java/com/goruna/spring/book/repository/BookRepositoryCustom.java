package com.goruna.spring.book.repository;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;

import java.util.List;

public interface BookRepositoryCustom {
    List<OwnerShopBooksResDTO> findOwnerShopBooksByUserSeq(Long userSeq);
}
