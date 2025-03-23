package com.moaguide.refactor.cow.entity;

import com.moaguide.dto.NewDto.customDto.CattleFarmDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattleFarmRepository extends JpaRepository<CattleFarm, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattleFarmDto(cf.date, cf.value)" +
            "FROM CattleFarm cf " +
            "WHERE cf.date >= :date " +
            "ORDER BY YEAR(cf.date), MONTH(cf.date), cf.value")
    List<CattleFarmDto> findCattleFarm(@Param("date") LocalDate date);
}
