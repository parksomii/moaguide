package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.BuildingDto.PopulationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuildingPopulationDto {
    private List<PopulationDto> populations;
}
