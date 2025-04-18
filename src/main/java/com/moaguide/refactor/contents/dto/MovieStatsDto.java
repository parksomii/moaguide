package com.moaguide.refactor.contents.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieStatsDto {

	private Date day;
	private String region;
	private int screenCount;
	private Long totalRevenue;
	private Double revenueShare;
	private Long totalAudience;
	private Double audienceShare;
	private Date releaseDate;
}
