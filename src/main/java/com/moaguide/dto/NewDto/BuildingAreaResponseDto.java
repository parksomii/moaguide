package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.BuildingDto.AreaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuildingAreaResponseDto {
    private List<AreaDto> areas;
}
