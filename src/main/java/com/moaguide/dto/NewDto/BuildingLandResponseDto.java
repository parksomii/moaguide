package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuildingLandResponseDto {
    private List<LandDto> lands;
}
