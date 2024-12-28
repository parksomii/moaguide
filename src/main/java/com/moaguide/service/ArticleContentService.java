package com.moaguide.service;

import com.moaguide.domain.ArticleContent.ArticleContent;
import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.domain.CategoryContent.Category;
import com.moaguide.dto.ContentDto;
import com.moaguide.dto.ContentOverviewDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleContentService {

    private final ArticleContentRepository articleContentRepository;

    // 페이지 리스트
    public Page<ContentDto> getContentsByCategory(int categoryId, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return articleContentRepository.findByCategoryId(categoryId, pageable)
                .map(this::mapToContentDto);
    }

    // 인기 콘텐츠 (조회수 기준 정렬)
    public List<ContentOverviewDto> getPopularContents(int popularLimit) {
        Pageable pageable = PageRequest.of(0, popularLimit, Sort.by(Sort.Direction.DESC, "views"));
        return articleContentRepository.findContentsWithCategory(pageable)
                .getContent();
    }

    // 최신 콘텐츠 (최신 날짜 기준 정렬)
    public List<ContentOverviewDto> getRecentContents(int recentLimit) {
        Pageable pageable = PageRequest.of(0, recentLimit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return articleContentRepository.findContentsWithCategory(pageable)
                .getContent();
    }

    // 최신 뉴스 클리핑 (최신 날짜 기준 정렬)
    public List<ContentOverviewDto> getLatestEconomicIssues(Category category, int newsLimit) {
        Pageable pageable = PageRequest.of(0, newsLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

        return articleContentRepository.findByCategory(category.getId(), pageable)
                .getContent();
    }

    public Page<ContentDto> getContentsByAll(Category category, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        if (category == Category.ALL) { // 카테고리 Enum 비교
            return articleContentRepository.findAll(pageable).map(this::mapToContentDto);
        } else {
            return articleContentRepository.findByCategoryId(category.getId(), pageable)
                    .map(this::mapToContentDto);
        }
    }


    public Page<ContentDto> getContentsByType(String type, Category category, int page) {
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
    private ContentDto mapToContentDto(ArticleContent content) {
        return new ContentDto(
                content.getContentId(),
                content.getTitle(),
                content.getType(),
                content.isPremium(),
                content.getViews(),
                content.getCreatedAt(),
                content.getLikes(),
                content.getDescription(),
                content.getImg_link()
        );
    }
}
