package com.moaguide.dto.NewDto;


import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.NewDto.BuildingDto.LeaseDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;

@Getter
public class BuildingBaseResponseDto {
    private String product_Id;
    private String name;
    private String publisher; // 증권종류
    private int piece; // 증권수
    private int basePrice; // 1주당 발행액
    private String totalPrice;
    private String subscription; // 청약일정 YYYY.MM.DD ~ YYYY.MM.DD
    private String listingDate; // 상장일 YYYY-MM-DD
    private String address; // 주소
    private String useArea; // 용도 지역
    private String mainUse; // 주용도
    private String completionDate; // 완공일 YYYY년 MM월 DD일
    private String landArea; // 대지면적 ----m^2
    private double floorAreaRate; // 용적율
    private double dryRatio; // 건폐율
    private double height; // 높이
    private String scale; // 건물 규모
    private String mainStructure; // 주구조
    private int parking; // 주차대수
    private int lift; // 승강기
    private String landElevation; // 지형높이
    private String landShape; // 지형형상
    private String zoningNational; // 지역지구 등 지정여부 (국토의 계획 및 이용에 관한 법률)
    private String zoningOther; // 지역지구 등 지정여부 (기타법률)
    private double latitude; // 위도
    private double longitude; // 경도
    private List<LeaseDto> leases;

    public BuildingBaseResponseDto(BuildingDetail buildingDetail, List<LeaseDto> leaseList, Transaction transaction, Location location, LandRegistry landRegistry) {
        this.product_Id = buildingDetail.getProductId().getProductId();
        this.name = buildingDetail.getProductId().getName();
        this.publisher = buildingDetail.getPublisher();
        this.piece = buildingDetail.getProductId().getPiece();
        this.basePrice = buildingDetail.getIssuanceValue();
        Long total = (long) (transaction.getPiece() * transaction.getPrice());
        if (total >= 100000000) {
            BigDecimal totalPrice = new BigDecimal(total / 100000000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "억원";
        } else if (total >= 10000) {
            BigDecimal totalPrice = new BigDecimal(total / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "만원";
        } else {
            this.totalPrice = total + "원";
        };
        this.subscription = buildingDetail.getSubscriptionSchedule();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.listingDate = formatter.format(buildingDetail.getListingDate());
        this.address = buildingDetail.getLocation();
        this.useArea = buildingDetail.getUseArea();
        this.mainUse = buildingDetail.getMainUse();
        this.completionDate = buildingDetail.getCompletionDate();
        this.landArea = buildingDetail.getLandArea();
        this.floorAreaRate = buildingDetail.getFloorAreaRate();
        this.dryRatio = buildingDetail.getDryRatio();
        this.height = buildingDetail.getHeight();
        this.scale = buildingDetail.getScale();
        this.mainStructure = buildingDetail.getMainStructure();
        this.parking = buildingDetail.getParking();
        this.lift = buildingDetail.getLift();
        this.landElevation = landRegistry.getLandElevation();
        this.landShape = landRegistry.getLandShape();
        this.zoningNational = landRegistry.getZoningNational();
        this.zoningOther = landRegistry.getZoningOther();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.leases = leaseList;
    }
}
