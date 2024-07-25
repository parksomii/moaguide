package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.BuildingDto.LandDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuildingLandResponseDto {
    private List<LandDto> lands;
}
