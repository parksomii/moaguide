package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AveragePriceRepository extends JpaRepository<AveragePrice, Long> {
    /*@Query("SELECT new com.moaguide.dto.NewDto.customDto.AveragePriceDto(CONCAT(CAST(YEAR(a.prdDe) AS string), " +
            "CASE WHEN MONTH(a.prdDe) < 10 THEN CONCAT('0', CAST(MONTH(a.prdDe) AS string)) ELSE CAST(MONTH(a.prdDe) AS string) END), " +
            "AVG(a.prdDt)) " +
            "FROM AveragePrice a " +
            "WHERE a.cType = :category AND YEAR(a.prdDe) >= :year " +
            "GROUP BY YEAR(a.prdDe), MONTH(a.prdDe) " +
            "ORDER BY YEAR(a.prdDe), MONTH(a.prdDe)")*/
    /*@Query("SELECT new com.moaguide.dto.NewDto.customDto.AveragePriceDto(YEAR(a.prdDe), MONTH(a.prdDe), AVG(a.prdDt)) " +
            "FROM AveragePrice a " +
            "WHERE a.cType = :category AND YEAR(a.prdDe) >= :year " +
            "GROUP BY YEAR(a.prdDe), MONTH(a.prdDe) " +
            "ORDER BY YEAR(a.prdDe), MONTH(a.prdDe)")*/
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.AveragePriceDto(a.prdDe, AVG(a.prdDt)) " +
            "FROM AveragePrice a " +
            "WHERE a.cType = :category AND a.prdDe >= :year " +
            "GROUP BY YEAR(a.prdDe), MONTH(a.prdDe) " +
            "ORDER BY YEAR(a.prdDe), MONTH(a.prdDe)")
    List<AveragePriceDto> findAveragePrice(@Param("category") String category, @Param("year") LocalDate year);
}