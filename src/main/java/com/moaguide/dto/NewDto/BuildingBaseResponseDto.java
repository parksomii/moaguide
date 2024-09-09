package com.moaguide.dto.NewDto;


import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingDetailDto;
import com.moaguide.dto.NewDto.BuildingDto.LeaseDto;
import com.moaguide.dto.NewDto.BuildingDto.PublishDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuildingBaseResponseDto {
    private PublishDto publish;
    private BuildingDetailDto buildingDetail;
    private LandRegistry landRegistry;
    private List<LeaseDto> lease;

    public BuildingBaseResponseDto(BuildingBaseDto building, List<LeaseDto> leaseDtos) {
        this.publish = new PublishDto(
                building.getName(),
                building.getPublisher(),
                building.getPiece(),
                building.getLastDivide(),
                building.getBasePrice(),
                building.getTotalPrice(),
                building.getSubscription(),
                building.getListingDate()
        );

        this.buildingDetail = new BuildingDetailDto(
                building.getUseArea(),
                building.getMainUse(),
                building.getCompletionDate(),
                building.getLandArea(),
                building.getFloorArea(),
                building.getFloorAreaRate(),
                building.getDryRatio(),
                building.getHeight(),
                building.getScale(),
                building.getMainStructure(),
                building.getParking(),
                building.getLift(),
                building.getLocation()
        );

        this.landRegistry = new LandRegistry(
                building.getLandElevation(),
                building.getLandShape(),
                building.getRoadInterface(),
                building.getZoningNational(),
                building.getZoningOther()
        );

        this.lease = leaseDtos; // Lease 정보는 그대로 사용
    }

}
