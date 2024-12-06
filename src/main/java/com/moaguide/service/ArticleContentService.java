package com.moaguide.service;

import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.dto.ContentDto;
import com.moaguide.dto.ContentOverviewDto;
import java.util.List;
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
public class ArticleContentService {

    private final ArticleContentRepository articleContentRepository;

    // 페이지 리스트
    public Page<ContentDto> getContentsByCategory(int categoryId, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return articleContentRepository.findByCategoryId(categoryId, pageable)
                .map(content -> new ContentDto(
                        content.getContentId(),
                        content.getTitle(),
                        content.getType(),
                        content.isPremium(),
                        content.getViews(),
                        content.getCreatedAt(),
                        content.getLikes(),
                        content.getDescription(),
                        content.getImg_link()
                ));
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
    public List<ContentOverviewDto> getLatestEconomicIssues(int categoryId, int newsLimit) {
        Pageable pageable = PageRequest.of(0, newsLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

        return articleContentRepository.findByCategory(categoryId, pageable)
                .getContent();
    }

    public Page<ContentDto> getContentsByAll(int categoryId, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (categoryId == 0) {
            return articleContentRepository.findAll(pageable).map(
                    content -> new ContentDto(
                            content.getContentId(),
                            content.getTitle(),
                            content.getType(),
                            content.isPremium(),
                            content.getViews(),
                            content.getCreatedAt(),
                            content.getLikes(),
                            content.getDescription(),
                            content.getImg_link()));
        } else {
            return articleContentRepository.findByCategoryId(categoryId, pageable).map(content -> new ContentDto(
                    content.getContentId(),
                    content.getTitle(),
                    content.getType(),
                    content.isPremium(),
                    content.getViews(),
                    content.getCreatedAt(),
                    content.getLikes(),
                    content.getDescription(),
                    content.getImg_link()));
        }
    }

    public Page<ContentDto> getContentsByType(String type, int categoryId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (categoryId == 0) {
            return articleContentRepository.findAllByType(type,pageable).map(
                    content -> new ContentDto(
                            content.getContentId(),
                            content.getTitle(),
                            content.getType(),
                            content.isPremium(),
                            content.getViews(),
                            content.getCreatedAt(),
                            content.getLikes(),
                            content.getDescription(),
                            content.getImg_link()));
        } else {
            return articleContentRepository.findByTypeAndCategoryId(type,categoryId, pageable).map(content -> new ContentDto(
                    content.getContentId(),
                    content.getTitle(),
                    content.getType(),
                    content.isPremium(),
                    content.getViews(),
                    content.getCreatedAt(),
                    content.getLikes(),
                    content.getDescription(),
                    content.getImg_link()));
        }
    }

}
