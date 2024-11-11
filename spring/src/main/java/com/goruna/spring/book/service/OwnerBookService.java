package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;
import com.goruna.spring.book.dto.UpdateBookInfoReqDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.common.util.CustomUserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerBookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void updateBookInfo(Long bookSeq, UpdateBookInfoReqDTO updateBookInfoReqDTO) {
        Book book = bookRepository.findById(bookSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        modelMapper.map(updateBookInfoReqDTO, book);
    }

    @Transactional
    public List<OwnerShopBooksResDTO> getOwnerShopBooks(Long userSeq) {
        List<OwnerShopBooksResDTO> books = bookRepository.findOwnerShopBooksByUserSeq(userSeq);

        return books;
    }
}
