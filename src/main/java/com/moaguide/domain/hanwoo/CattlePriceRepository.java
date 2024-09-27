package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.CattleFarmDto;
import com.moaguide.dto.NewDto.customDto.CattlePriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattlePriceRepository extends JpaRepository<CattlePrice, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattlePriceDto(cf.date, SUM(cf.value))" +
            "FROM CattleFarm cf " +
            "WHERE cf.date >= :date " +
            "GROUP BY YEAR(cf.date), MONTH(cf.date), cf.value " +
            "ORDER BY YEAR(cf.date), MONTH(cf.date), cf.value")
    List<CattlePriceDto> findCattlePrice(@Param("date") LocalDate date);
}
