package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.product.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieStatsDto {

    private Date day;
    private String region;
    private int screenCount;
    private Long totalRevenue;
    private Double revenueShare;
    private Long totalAudience;
    private Double audienceShare;
    private Date releaseDate;
}
