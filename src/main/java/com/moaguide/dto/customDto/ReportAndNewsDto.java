package com.moaguide.dto.customDto;

import com.moaguide.dto.customDto.NewsCustomDto;
import com.moaguide.dto.customDto.ReportCustomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportAndNewsDto {
    private List<ReportCustomDto> mainReport; // 주요 리포트
    private List<NewsCustomDto> mainNews; // 최신 이슈
}
