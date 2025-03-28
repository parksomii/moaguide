package com.moaguide.dto.NewDto.customDto;

import com.moaguide.refactor.news.dto.NewsCustomDto;
import com.moaguide.refactor.product.dto.ArticleSummaryDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportAndNewsDto {

	private List<ArticleSummaryDto> mainReport; // 주요 리포트
	private List<NewsCustomDto> mainNews; // 최신 이슈
}