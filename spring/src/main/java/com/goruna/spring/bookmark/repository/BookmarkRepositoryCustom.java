package com.goruna.spring.bookmark.repository;

import com.goruna.spring.bookmark.entity.Bookmark;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<Bookmark> findBookmarksByUserSeq(Long userSeq);
}
