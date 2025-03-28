package com.moaguide.refactor.product.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class DetailReportResponseDto {

	private final List<ReportCustomDto> report;
	private final int total;
	private final int page;
	private final int size;


	public DetailReportResponseDto(Page<ReportCustomDto> report) {
		this.report = report.getContent();
		this.total = report.getTotalPages();
		this.page = report.getNumber() - 1;
		this.size = report.getSize();
	}
}
