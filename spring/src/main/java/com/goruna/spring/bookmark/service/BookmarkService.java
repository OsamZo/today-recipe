package com.goruna.spring.bookmark.service;

import com.goruna.spring.bookmark.dto.BookmarkResponseDTO;
import com.goruna.spring.bookmark.entity.Bookmark;
import com.goruna.spring.bookmark.repository.BookmarkRepository;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /* 북마크 추가 */
    @Transactional
    public BookmarkResponseDTO createBookmark(Long userSeq, Long shopSeq){

        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .shop(shop)
                .build();
        bookmarkRepository.save(bookmark);

        return modelMapper.map(bookmark, BookmarkResponseDTO.class);
    }

    /* 북마크 삭제 */
    @Transactional
    public void deleteBookmark(Long bookmarkSeq) {
        bookmarkRepository.deleteById(bookmarkSeq);
    }
}
