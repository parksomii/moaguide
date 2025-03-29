package com.moaguide.refactor.contents.dto;

import com.moaguide.refactor.contents.entity.movie.MovieSchedule;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ContentsSubResponseDto {

	private List<MovieSchedule> schedules;
	private List<MovieStatsDto> stats;

	public ContentsSubResponseDto(List<MovieSchedule> schedules, List<MovieStatsDto> stats) {
		if (schedules == null || schedules.isEmpty()) {
			this.schedules = new ArrayList<>();
		} else {
			this.schedules = schedules;
		}
		if (stats == null || stats.isEmpty()) {
			this.stats = new ArrayList<>();
		} else {
			this.stats = stats;
		}
	}
}
