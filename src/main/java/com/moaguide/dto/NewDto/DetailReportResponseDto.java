package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class DetailReportResponseDto {
    private List<ReportCustomDto> report;
    private int total;
    private int page;
    private int size;


    public DetailReportResponseDto(Page<ReportCustomDto> report) {
        this.report = report.getContent();
        this.total = report.getTotalPages();
        this.page = report.getNumber() -1;
        this.size = report.getSize();
    }
}
