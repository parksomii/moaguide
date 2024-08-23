package com.moaguide.dto;

import com.moaguide.domain.building.districts.Districts;
import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.summary.Summary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDetailDto {

    private Summary productId;

    private String useArea;

    private String mainUse;

    private String completionDate;

    private String landArea;

    private String floorArea;

    private Double floorAreaRate;

    private Double dryRatio;

    private Double height;

    private String scale;

    private String mainStructure;

    private Integer parking;

    private Integer lift;

    private String location;


    public BuildingDetailDto(BuildingDetail buildingDetail) {
        this.productId = buildingDetail.getProductId();
        this.useArea = buildingDetail.getUseArea();
        this.mainUse = buildingDetail.getMainUse();
        this.completionDate = buildingDetail.getCompletionDate();
        this.landArea = buildingDetail.getLandArea();
        this.floorArea = buildingDetail.getFloorArea();
        this.floorAreaRate = buildingDetail.getFloorAreaRate();
        this.dryRatio = buildingDetail.getDryRatio();
        this.height = buildingDetail.getHeight();
        this.scale = buildingDetail.getScale();
        this.mainStructure = buildingDetail.getMainStructure();
        this.parking = buildingDetail.getParking();
        this.lift = buildingDetail.getLift();
        this.location = buildingDetail.getLocation();
    }
}
