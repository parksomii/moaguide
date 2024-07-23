package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingSubwayResponseDto {
    private SubwayTimeDto subwayTime;
    private List<SubwayWeekDto> subwayWeek;
}
