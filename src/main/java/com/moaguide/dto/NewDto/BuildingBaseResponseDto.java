package com.moaguide.dto.NewDto;


import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.dto.BuildingDetailDto;
import com.moaguide.dto.NewDto.BuildingDto.LeaseDto;
import com.moaguide.dto.NewDto.BuildingDto.PublishDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuildingBaseResponseDto {
    private String product_Id;
    private PublishDto publish;
    private BuildingDetailDto buildingDetail;
    private LandRegistry landRegistry;
    private List<LeaseDto> lease;
}
