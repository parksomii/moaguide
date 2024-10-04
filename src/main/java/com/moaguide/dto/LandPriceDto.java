package com.moaguide.dto;

import com.moaguide.domain.product.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LandPriceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Product productId;

    private int landPrice;

    private String baseYear;

    private String baseDay;

    private Date noticeDay;

    public LandPriceDto(int landPrice, String baseYear) {
        this.landPrice = landPrice;
        this.baseYear = baseYear;
    }
}
