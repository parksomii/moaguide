package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.CattleSaleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattleSaleRepository extends JpaRepository<CattleSale, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattleSaleDto(cs.date, CAST(SUM(cs.value) AS long))" +
            "FROM CattleSale cs " +
            "WHERE cs.date >= :date " +
            "GROUP BY YEAR(cs.date), MONTH(cs.date), cs.value " +
            "ORDER BY YEAR(cs.date), MONTH(cs.date), cs.value")
    List<CattleSaleDto> findCattleSale(@Param("date") LocalDate date);
}
