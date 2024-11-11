package com.goruna.spring.bookmark;

import com.goruna.spring.bookmark.dto.BookmarkCreatResDTO;
import com.goruna.spring.bookmark.dto.BookmarkReadResDTO;
import com.goruna.spring.bookmark.entity.Bookmark;
import com.goruna.spring.bookmark.repository.BookmarkRepository;
import com.goruna.spring.bookmark.service.BookmarkService;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User mockUser;
    private Shop mockShop;
    private Bookmark mockBookmark;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 간단한 mock 객체 초기화
        mockUser = mock(User.class);
        mockShop = mock(Shop.class);
        mockBookmark = mock(Bookmark.class);

        // 기본 필드값 설정
        when(mockUser.getUserSeq()).thenReturn(1L);
        when(mockShop.getShopSeq()).thenReturn(1L);
        when(mockBookmark.getBookmarkSeq()).thenReturn(1L);
        when(mockBookmark.getUser()).thenReturn(mockUser);
        when(mockBookmark.getShop()).thenReturn(mockShop);
    }

    @Test
    void createBookmark_ShouldReturnBookmarkCreatResDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(shopRepository.findById(1L)).thenReturn(Optional.of(mockShop));
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(mockBookmark);

        BookmarkCreatResDTO mockResponse = new BookmarkCreatResDTO();
        when(modelMapper.map(any(Bookmark.class), eq(BookmarkCreatResDTO.class))).thenReturn(mockResponse);

        BookmarkCreatResDTO response = bookmarkService.createBookmark(1L, 1L);

        assertNotNull(response);
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
    }

    @Test
    void deleteBookmark_ShouldCallDeleteById() {
        Long bookmarkSeq = 1L;

        doNothing().when(bookmarkRepository).deleteById(bookmarkSeq);

        bookmarkService.deleteBookmark(bookmarkSeq);

        verify(bookmarkRepository, times(1)).deleteById(bookmarkSeq);
    }

}
