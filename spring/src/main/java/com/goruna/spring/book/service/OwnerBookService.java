package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.util.CustomUserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerBookService {

    private final BookRepository bookRepository;

    public List<OwnerShopBooksResDTO> getOwnerShopBooks() {

//        Long userSeq = CustomUserUtils.getCurrentUserSeq();   // 토큰에서 추출
        Long userSeq = 3L;
        List<OwnerShopBooksResDTO> books = bookRepository.findOwnerShopBooksByUserSeq(userSeq);

        return books;
    }
}


