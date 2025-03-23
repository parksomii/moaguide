package com.moaguide.service.ArticleContent;

import com.moaguide.refactor.article.repository.ArticleContentRepository;
import com.moaguide.refactor.product.entity.CategoryContent.Category;
import com.moaguide.dto.NewDto.ArticleContentDto.ArticleOverviewDto;
import com.moaguide.refactor.util.TimeServie;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleOverviewService {

	private final ArticleContentRepository articleContentRepository;

	// 인기 콘텐츠 (조회수 기준 정렬)
	public List<ArticleOverviewDto> getPopularContents(int popularLimit) {
		Pageable pageable = PageRequest.of(0, popularLimit, Sort.by(Sort.Direction.DESC, "views"));
		return articleContentRepository.findContentsByViews(pageable,TimeServie.getNowTimestamp())
			.getContent();
	}

	// 최신 콘텐츠 (최신 날짜 기준 정렬)
	public List<ArticleOverviewDto> getRecentContents(int recentLimit) {
		Pageable pageable = PageRequest.of(0, recentLimit,
			Sort.by(Sort.Direction.DESC, "createdAt"));
		return articleContentRepository.findContentsWithCategory(pageable,TimeServie.getNowTimestamp())
			.getContent();
	}

	// 최신 뉴스 클리핑 (최신 날짜 기준 정렬)
	public List<ArticleOverviewDto> getNewsContents(Category category, int newsLimit) {
		Pageable pageable = PageRequest.of(0, newsLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

		return articleContentRepository.findByCategory(category.getId(), pageable, TimeServie.getNowTimestamp())
			.getContent();
	}
}
