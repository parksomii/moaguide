package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportAndNewsDto {
    private List<ArticleSummaryDto> mainReport; // 주요 리포트
    private List<NewsCustomDto> mainNews; // 최신 이슈
}
