package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildingBaseDto {
    private String name;

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

    private String landElevation;

    private String landShape;

    private String roadInterface;

    private String zoningNational;

    private String zoningOther;

    private String publisher; // 증권종류

    private int piece; // 증권수

    private Double lastDivide;

    private int basePrice; // 1주당 발행액

    private String totalPrice;

    private String subscription; // 청약일정 YYYY.MM.DD ~ YYYY.MM.DD

    private String listingDate; // 상장일 YYYY-MM-DD
}
