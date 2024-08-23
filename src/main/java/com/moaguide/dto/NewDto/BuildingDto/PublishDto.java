package com.moaguide.dto.NewDto.BuildingDto;

import com.moaguide.domain.detail.BuildingDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class PublishDto {
    private String name;
    private String publisher; // 증권종류
    private int piece; // 증권수
    private int basePrice; // 1주당 발행액
    private String totalPrice;
    private String subscription; // 청약일정 YYYY.MM.DD ~ YYYY.MM.DD
    private String listingDate; // 상장일 YYYY-MM-DD

    public PublishDto(BuildingDetail buildingDetail) {
        this.name=buildingDetail.getProductId().getName();
        this.publisher = getPublisher();
        this.piece = buildingDetail.getProductId().getPiece();
        // 발행액과 증권 수를 곱한 값 계산
        Long total = (long) buildingDetail.getIssuanceValue() * getPiece();

        // 값이 100,000,000 이상이면 "억원", 10,000 이상이면 "만원", 그 이하면 "원" 붙이기
        if (total >= 100000000) {
            // 100,000,000으로 나눈 후 소수점 두 자리까지 표현
            BigDecimal totalInBillions = new BigDecimal(total).divide(new BigDecimal(100000000), 2, RoundingMode.HALF_UP);
            this.totalPrice = totalInBillions + "억원";
        } else if (total >= 10000) {
            // 10,000으로 나눈 후 소수점 두 자리까지 표현
            BigDecimal totalInTenThousands = new BigDecimal(total).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP);
            this.totalPrice = totalInTenThousands + "만원";
        } else {
            // 10,000 미만인 경우
            this.totalPrice = total + "원";
        }
        this.subscription = buildingDetail.getProductId().getName();
        this.listingDate = getListingDate();
    }
}
