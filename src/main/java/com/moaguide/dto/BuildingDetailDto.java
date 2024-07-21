package com.moaguide.dto;

import com.moaguide.domain.building.districts.Districts;
import com.moaguide.domain.summary.Summary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDetailDto {

    private Integer id;

    private Summary productId;

    private Districts districtsId;

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

    private String securities;

    private String publisher;

    private Integer issuanceValue;

    private String subscriptionSchedule;

    private Date listingDate;

    private Date initialDividendBaseDate;

    private Integer dividendCycle;

    private String keyword;

    public BuildingDetailDto(Summary productId, Districts districtsId, String useArea, String mainUse,
                             String completionDate, String landArea, String floorArea, Double floorAreaRate,
                             Double dryRatio, Double height, String scale, String mainStructure, Integer parking,
                             Integer lift, String location, String securities, String publisher, Integer issuanceValue,
                             String subscriptionSchedule, Date listingDate, Date initialDividendBaseDate, Integer dividendCycle,
                             String keyword) {
        this.productId = productId;
        this.districtsId = districtsId;
        this.useArea = useArea;
        this.mainUse = mainUse;
        this.completionDate = completionDate;
        this.landArea = landArea;
        this.floorArea = floorArea;
        this.floorAreaRate = floorAreaRate;
        this.dryRatio = dryRatio;
        this.height = height;
        this.scale = scale;
        this.mainStructure = mainStructure;
        this.parking = parking;
        this.lift = lift;
        this.location = location;
        this.securities = securities;
        this.publisher = publisher;
        this.issuanceValue = issuanceValue;
        this.subscriptionSchedule = subscriptionSchedule;
        this.listingDate = listingDate;
        this.initialDividendBaseDate = initialDividendBaseDate;
        this.dividendCycle = dividendCycle;
        this.keyword = keyword;
    }
}
