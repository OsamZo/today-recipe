package com.goruna.spring.users.service;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.users.dto.NickNameRequestDto;
import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.dto.UserInfoResponse;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void createNickname(Long userSeq, NickNameRequestDto nickNameRequestDto) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(EntityNotFoundException::new);
        modelMapper.map(nickNameRequestDto, user);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userSeq) {
        User user = userRepository.getUserInfo(userSeq);
        return modelMapper.map(user, UserInfoResponse.class);
    }

    @Transactional
    public UserInfoRequestDto updateUserInfo(UserInfoRequestDto userInfoRequestDto) {
        User user = userRepository.getUserNickName(CustomUserUtils.getCurrentUserSeq(), userInfoRequestDto);
        return modelMapper.map(user, UserInfoRequestDto.class);
    }

    @Transactional(readOnly = true)
    public List<BookListReadResDTO> readUserHistory(Long userSeq) {
        List<Book> books = bookRepository.findByUserUserSeq(userSeq);
        return books.stream()
                .map(book -> {
                    BookListReadResDTO bookListReadResDTO = modelMapper.map(book, BookListReadResDTO.class);
                    bookListReadResDTO.setShopSeq(book.getProduct().getShop().getShopSeq());
                    bookListReadResDTO.setShopName(book.getProduct().getShop().getShopName());
                    bookListReadResDTO.setShopClosedAt(book.getProduct().getProductClosedAt());
                    bookListReadResDTO.setShopAddress(book.getProduct().getShop().getShopAddress());
                    bookListReadResDTO.setShopImgUrl(book.getProduct().getShop().getShopImgUrl());
                    bookListReadResDTO.setIsBookCancelled(book.getIsBookCancelled());
                    bookListReadResDTO.setProductOriginalPrice(book.getProduct().getProductOriginalPrice());
                    bookListReadResDTO.setProductSalePrice(book.getProduct().getProductSalePrice());
                    return bookListReadResDTO;
                })
                .collect(Collectors.toList());
    }
}