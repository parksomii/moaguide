package com.moaguide.service.ArticleContent;

import com.moaguide.domain.ArticleContent.ArticleContent;
import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.domain.CategoryContent.Category;
import com.moaguide.dto.ArticleQueryDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleQueryService {
    private final ArticleContentRepository articleContentRepository;

    // 카테고리별 조회
    public Page<ArticleQueryDto> getContentsByCategory(int categoryId, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return articleContentRepository.findByCategoryId(categoryId, pageable)
                .map(this::mapToContentDto);
    }

    // 전체 콘텐츠 조회
    public Page<ArticleQueryDto> getContentsByAll(Category category, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (category == Category.ALL) {
            return articleContentRepository.findAll(pageable).map(this::mapToContentDto);
        } else {
            return articleContentRepository.findByCategoryId(category.getId(), pageable)
                    .map(this::mapToContentDto);
        }
    }

    // 타입별 조회
    public Page<ArticleQueryDto> getContentsByType(String type, Category category, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        if (category == Category.ALL) {
            return articleContentRepository.findAllByType(type, pageable)
                    .map(this::mapToContentDto);
        } else {
            return articleContentRepository.findByTypeAndCategoryId(type, category.getId(), pageable)
                    .map(this::mapToContentDto);
        }
    }

    // 공통 DTO 매핑 메서드
    private ArticleQueryDto mapToContentDto(ArticleContent articleContent) {
        return new ArticleQueryDto(
                articleContent.getArticleId(),
                articleContent.getTitle(),
                articleContent.getType(),
                articleContent.getIsPremium(),
                articleContent.getViews(),
                articleContent.getCreatedAt(),
                articleContent.getLikes(),
                articleContent.getPaywallUp(),
                articleContent.getImgLink()
        );
    }
}
