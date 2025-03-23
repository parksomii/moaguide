package com.moaguide.service.ArticleContent;

import com.moaguide.refactor.article.entity.ArticleContent;
import com.moaguide.refactor.article.repository.ArticleContentRepository;
import com.moaguide.refactor.article.repository.ArticleLikeRepository;
import com.moaguide.refactor.product.entity.CategoryContent.Category;
import com.moaguide.dto.NewDto.ArticleContentDto.ArticleQueryDto;
import com.moaguide.refactor.util.TimeServie;
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
		return articleContentRepository.findByCategoryId(categoryId, pageable,TimeServie.getNowTimestamp())
			.map(this::mapToContentDto);
	}

	// 전체 콘텐츠 조회
	public Page<ArticleQueryDto> getContentsByAll(Category category, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
		if (category == Category.ALL) {
			return articleContentRepository.findAllContent(pageable,TimeServie.getNowTimestamp()).map(this::mapToContentDto);
		} else {
			return articleContentRepository.findByCategoryId(category.getId(), pageable,TimeServie.getNowTimestamp())
				.map(this::mapToContentDto);
		}
	}

	// 타입별 조회
	public Page<ArticleQueryDto> getContentsByType(String type, Category category, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
		if (category == Category.ALL) {
			return articleContentRepository.findByTypeContent(type, pageable, TimeServie.getNowTimestamp())
				.map(this::mapToContentDto);
		} else {
			return articleContentRepository.findByTypeAndCategoryId(type, category.getId(),
					pageable,TimeServie.getNowTimestamp())
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
