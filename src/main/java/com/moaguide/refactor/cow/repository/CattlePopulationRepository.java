package com.moaguide.refactor.cow.repository;

import com.moaguide.dto.NewDto.customDto.CattlePopulationDto;
import com.moaguide.refactor.cow.entity.CattlePopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattlePopulationRepository extends JpaRepository<CattlePopulation, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattlePopulationDto(cp.date,cp.value)" +
            "FROM CattlePopulation cp " +
            "WHERE cp.date >= :date " +
            "ORDER BY YEAR(cp.date), MONTH(cp.date), cp.value")
    List<CattlePopulationDto> findCattlePopulation(@Param("date") LocalDate date);
}
