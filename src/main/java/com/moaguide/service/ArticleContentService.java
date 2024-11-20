package com.moaguide.service;

import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.dto.ContentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleContentService {
    private final ArticleContentRepository articleContentRepository;

    public Page<ContentDto> getContentsByCategory(int categoryId, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<ContentDto> article = articleContentRepository.findByCategoryId(categoryId, pageable)
                .map(content -> new ContentDto(
                        content.getContentId(),
                        content.getTitle(),
                        content.getType(),
                        content.isPremium(),
                        content.getViews(),
                        content.getCreatedAt(),
                        content.getLikes(),
                        content.getComments()
                ));
        return article;

    }
}