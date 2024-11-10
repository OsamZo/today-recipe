package com.goruna.spring.book.repository;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepositoryCustom {

    List<OwnerShopBooksResDTO> findOwnerShopBooksByUserSeq(Long userSeq);

    Long CountTodayBookByUserSeqAndShopSeq(Long userSeq, Long shopSeq, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
