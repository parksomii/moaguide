package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryCustomDto {
    // 주요 상품 현황 및 상품 목록
    private String product_Id;
    private String name;
    private Long totalPrice;
    private Integer price;
    private double priceRate;
    private double lastDivide_rate;
    private String category;
    private String platform;
}
