package com.moaguide.dto.NewDto;

import com.moaguide.refactor.contents.entity.MovieSchedule;
import com.moaguide.dto.NewDto.customDto.MovieStatsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class ContentsSubResponseDto {
    private List<MovieSchedule> schedules;
    private List<MovieStatsDto> stats;

    public ContentsSubResponseDto(List<MovieSchedule> schedules, List<MovieStatsDto> stats) {
        if(schedules == null || schedules.isEmpty()){
            this.schedules = new ArrayList<>();
        }else{
            this.schedules = schedules;
        }
        if(stats == null || stats.isEmpty()){
            this.stats = new ArrayList<>();
        }else{
            this.stats = stats;
        }
    }
}
