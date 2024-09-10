package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AveragePriceRepository extends JpaRepository<AveragePrice, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.AveragePriceDto(MONTH(a.prdDe), AVG(a.prdDt)) " +
            "FROM AveragePrice a " +
            "WHERE a.cType = :category AND YEAR(a.prdDe) = :year " +
            "GROUP BY MONTH(a.prdDe)")
    List<AveragePriceDto> findAveragePrice(@Param("category") String category, @Param("year") int year);
}