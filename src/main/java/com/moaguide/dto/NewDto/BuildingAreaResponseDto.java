package com.moaguide.dto.NewDto;

import com.moaguide.domain.building.location.Location;
import com.moaguide.dto.NewDto.BuildingDto.AreaDto;
import jakarta.persistence.Column;
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

    public BuildingAreaResponseDto(String name, Location location, List<AreaDto> areas) {
        this.name = name;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.areas = areas;
    }
}
