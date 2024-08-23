package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@AllArgsConstructor
@Getter
public class BuildingReponseDto {
    private String Platform;
    private String Name;
    private long price;
    private double price_rate;
    private String totalPrice;
    private double last_divide;
    private double last_divide_rate;
    private int divide_cycle;

    public BuildingReponseDto(List<Transaction> transaction, Divide divide, Integer dividendCycle) {
        this.Platform = transaction.get(0).getPlatform();
        this.Name = transaction.get(0).getName();
        this.price = transaction.get(0).getPrice();
        // 트랜잭션이 2개 이상일 때, 가격 변동률 계산
        if (transaction.size() > 1) {
            long previousPrice = transaction.get(1).getPrice();  // 전날의 가격
            long todayPrice = transaction.get(0).getPrice();     // 오늘의 가격

            if (previousPrice != 0) {  // 0으로 나누는 오류 방지
                this.price_rate = ((todayPrice - previousPrice) / previousPrice) * 100;
            } else {
                this.price_rate = 0; // 전날의 가격이 0일 경우 변동률 0으로 처리
            }
        } else {
            this.price_rate = 0; // 트랜잭션이 1개 이하일 경우 변동률 0으로 처리
        }

        Long total = (long) transaction.get(0).getPiece() * transaction.get(0).getPiece();

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
        this.last_divide = divide.getDivideId();
        this.last_divide_rate = divide.getDividendRate();
        this.divide_cycle = dividendCycle;
    }
}
