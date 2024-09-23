package com.moaguide.service;

import com.moaguide.domain.bookmark.Bookmark;
import com.moaguide.domain.bookmark.BookmarkRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;

@AllArgsConstructor
@Getter
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public int countByUser(String username) {
        return bookmarkRepository.countByUser(username);
    }

    @Transactional
    public void postBookmark(String productId, String nickname) {
        try {
            bookmarkRepository.insertBookmark(productId, nickname);
        } catch (IllegalArgumentException e) {
            // 중복 북마크가 있을 경우 예외 처리
            throw new RuntimeException("이미 북마크가 되어 있습니다.");
        } catch (Exception e) {
            // 기타 예상치 못한 오류 처리
            throw new RuntimeException("북마크 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteBookmark(String productId, String nickname) {
        try {
            // productId와 nickname으로 해당 북마크를 찾아 삭제
            bookmarkRepository.deleteByProductIdAndNickName(productId, nickname);
        } catch (EmptyResultDataAccessException e) {
            // 북마크가 존재하지 않을 경우 처리
            throw new RuntimeException("북마크가 존재하지 않습니다.");
        } catch (Exception e) {
            // 기타 예상치 못한 오류 처리
            throw new RuntimeException("북마크 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
