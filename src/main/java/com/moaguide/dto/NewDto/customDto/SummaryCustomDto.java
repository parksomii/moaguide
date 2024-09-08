package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMapping(
        name = "SummaryMapping",
        classes = @ConstructorResult(
                targetClass = SummaryCustomDto.class,
                columns = {
                        @ColumnResult(name = "Product_Id", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "totalprice", type = Long.class),
                        @ColumnResult(name = "price", type = Integer.class),
                        @ColumnResult(name = "price_rate", type = Double.class),
                        @ColumnResult(name = "dividend", type = Double.class),
                        @ColumnResult(name = "dividend_Rate", type = Double.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class)
                }
        )
)

public class SummaryCustomDto {
    // 주요 상품 현황 및 상품 목록
    private String productId;
    private String name;
    private Long totalprice;
    private Integer price;
    private double priceRate;
    private double dividend;
    private double dividendRate;
    private String category;
    private String platform;
}
