package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.CattleFarmDto;
import com.moaguide.refactor.cow.entity.CattleFarm;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CattleFarmRepository extends JpaRepository<CattleFarm, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.CattleFarmDto(cf.date, cf.value)" +
		"FROM CattleFarm cf " +
		"WHERE cf.date >= :date " +
		"ORDER BY YEAR(cf.date), MONTH(cf.date), cf.value")
	List<CattleFarmDto> findCattleFarm(@Param("date") LocalDate date);
}
