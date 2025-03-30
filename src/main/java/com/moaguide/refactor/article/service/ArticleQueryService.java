package com.moaguide.refactor.article.service;

import com.moaguide.refactor.article.dto.ArticleQueryDto;
import com.moaguide.refactor.article.entity.ArticleContent;
import com.moaguide.refactor.article.repository.ArticleContentRepository;
import com.moaguide.refactor.article.repository.ArticleLikeRepository;
import com.moaguide.refactor.enums.ArticleCategory;
import com.moaguide.refactor.util.TimeUtil;
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
	private final ArticleLikeRepository articleLikeRepository;


	// 카테고리별 조회
	public Page<ArticleQueryDto> getContentsByCategory(int categoryId, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
		return articleContentRepository.findByCategoryId(categoryId, pageable,
				TimeUtil.getNowTimestamp())
			.map(this::mapToContentDto);
	}

	// 전체 콘텐츠 조회
	public Page<ArticleQueryDto> getContentsByAll(ArticleCategory articleCategory, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
		if (articleCategory == ArticleCategory.ALL) {
			return articleContentRepository.findAllContent(pageable, TimeUtil.getNowTimestamp())
				.map(this::mapToContentDto);
		} else {
			return articleContentRepository.findByCategoryId(articleCategory.getId(), pageable,
					TimeUtil.getNowTimestamp())
				.map(this::mapToContentDto);
		}
	}

	// 타입별 조회
	public Page<ArticleQueryDto> getContentsByType(String type, ArticleCategory articleCategory,
		int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
		if (articleCategory == ArticleCategory.ALL) {
			return articleContentRepository.findByTypeContent(type, pageable,
					TimeUtil.getNowTimestamp())
				.map(this::mapToContentDto);
		} else {
			return articleContentRepository.findByTypeAndCategoryId(type, articleCategory.getId(),
					pageable, TimeUtil.getNowTimestamp())
				.map(this::mapToContentDto);
		}
	}

	// 공통 DTO 매핑 메서드
	private ArticleQueryDto mapToContentDto(ArticleContent articleContent) {
		// 좋아요 수 계산
		int likes = articleLikeRepository.countLikesByArticleId(articleContent.getArticleId());
		return new ArticleQueryDto(
			articleContent.getArticleId(),
			articleContent.getTitle(),
			articleContent.getType(),
			articleContent.getIsPremium(),
			articleContent.getViews(),
			articleContent.getCreatedAt(),
			likes,
			articleContent.getPaywallUp(),
			articleContent.getImgLink()
		);
	}
}
