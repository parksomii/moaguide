package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.CattleSaleDto;
import com.moaguide.refactor.cow.entity.CattleSale;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CattleSaleRepository extends JpaRepository<CattleSale, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.CattleSaleDto(cs.date, cs.value)" +
		"FROM CattleSale cs " +
		"WHERE cs.date >= :date " +
		"ORDER BY YEAR(cs.date), MONTH(cs.date), cs.value")
	List<CattleSaleDto> findCattleSale(@Param("date") LocalDate date);
}
