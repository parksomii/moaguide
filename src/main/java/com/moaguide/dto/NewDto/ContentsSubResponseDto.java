package com.moaguide.dto.NewDto;

import com.moaguide.domain.content.movie.MovieSchedule;
import com.moaguide.domain.content.movie.MovieStats;
import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContentsSubResponseDto {
    private List<MovieSchedule> schedules;
    private List<MovieStats> stats;
}
