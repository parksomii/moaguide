package com.moaguide.dto.NewDto.BuildingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LeaseDto {

    private String tenant;

    private String tenantIntroduction;

    private String leasePeriod;

    private Double leaseArea;

    private Long deposit;

    private String rent;

    private String adminCost;

    private String detaile;

}
