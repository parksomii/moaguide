package com.moaguide.refactor.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportCustomDto {

	// 메인 페이지의 주요 리포트를 보여주기 위한 DTO
	private Long id;
	private String title;
	private String content;
	private String category;
	private Date date;
}
