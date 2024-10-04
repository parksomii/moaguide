package com.moaguide.dto.NewDto;

import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.BuildingDto.AreaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuildingAreaResponseDto {
    private String name;
    private double longitude;
    private double latitude;
    private List<AreaDto> areas;

    public BuildingAreaResponseDto(LocationDto location, List<AreaDto> areas) {
        this.name = location.getName();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.areas = areas;
    }
}
