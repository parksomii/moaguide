package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRecentDto {
    private List<SummaryDivideCustomDto> divide; // 최근 배당금 발표
    private List<SummaryCustomDto> summary; // 최근 상품
    private List<ArticleSummaryDto> article;
}
