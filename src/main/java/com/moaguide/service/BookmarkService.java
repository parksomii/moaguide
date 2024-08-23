package com.moaguide.service;

import com.moaguide.domain.bookmark.BookmarkRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public int countByUser(String username) {
        return bookmarkRepository.countByUser(username);
    }
}
