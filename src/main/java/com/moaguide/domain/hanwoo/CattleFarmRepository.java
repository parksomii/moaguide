package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.CattleFarmDto;
import com.moaguide.dto.NewDto.customDto.CattleSaleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattleFarmRepository extends JpaRepository<CattleFarm, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattleFarmDto(cf.date, SUM(cf.value))" +
            "FROM CattleFarm cf " +
            "WHERE cf.date >= :date " +
            "GROUP BY YEAR(cf.date), MONTH(cf.date), cf.value " +
            "ORDER BY YEAR(cf.date), MONTH(cf.date), cf.value")
    List<CattleFarmDto> findCattleSale(@Param("date") LocalDate date);
}
