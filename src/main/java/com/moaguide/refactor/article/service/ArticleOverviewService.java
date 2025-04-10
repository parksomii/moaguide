package com.moaguide.refactor.article.service;

import com.moaguide.refactor.article.dto.ArticleOverviewDto;
import com.moaguide.refactor.article.repository.ArticleContentRepository;
import com.moaguide.refactor.enums.ArticleCategory;
import com.moaguide.refactor.util.TimeUtil;
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
		return articleContentRepository.findContentsByViews(pageable, TimeUtil.getNowTimestamp())
			.getContent();
	}

	// 최신 콘텐츠 (최신 날짜 기준 정렬)
	public List<ArticleOverviewDto> getRecentContents(int recentLimit) {
		Pageable pageable = PageRequest.of(0, recentLimit,
			Sort.by(Sort.Direction.DESC, "createdAt"));
		return articleContentRepository.findContentsWithCategory(pageable,
				TimeUtil.getNowTimestamp())
			.getContent();
	}

	// 최신 뉴스 클리핑 (최신 날짜 기준 정렬)
	public List<ArticleOverviewDto> getNewsContents(ArticleCategory articleCategory,
		int newsLimit) {
		Pageable pageable = PageRequest.of(0, newsLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

		return articleContentRepository.findByCategory(articleCategory.getId(), pageable,
				TimeUtil.getNowTimestamp())
			.getContent();
	}
}
