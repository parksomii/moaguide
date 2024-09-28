package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.CattlePriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CattlePriceRepository extends JpaRepository<CattlePrice, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.CattlePriceDto(cp.date, cp.value)" +
            "FROM CattlePrice cp " +
            "WHERE cp.date >= :date " +
            "GROUP BY cp.date, cp.value " +
            "ORDER BY cp.date, cp.value")
    List<CattlePriceDto> findCattlePrice(@Param("date") LocalDate date);
}
