package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.CattlePopulationDto;
import com.moaguide.refactor.cow.entity.CattlePopulation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CattlePopulationRepository extends JpaRepository<CattlePopulation, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.CattlePopulationDto(cp.date,cp.value)" +
		"FROM CattlePopulation cp " +
		"WHERE cp.date >= :date " +
		"ORDER BY YEAR(cp.date), MONTH(cp.date), cp.value")
	List<CattlePopulationDto> findCattlePopulation(@Param("date") LocalDate date);
}
