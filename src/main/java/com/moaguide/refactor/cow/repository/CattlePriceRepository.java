package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.CattleTransactionDto;
import com.moaguide.refactor.cow.entity.CattlePrice;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CattlePriceRepository extends JpaRepository<CattlePrice, Long> {

	@Query(
		"SELECT new com.moaguide.refactor.cow.dto.CattleTransactionDto(YEAR(cp.date), MONTH(cp.date), SUM(cp.value))"
			+
			"FROM CattlePrice cp " +
			"WHERE cp.date >= :date " +
			"GROUP BY YEAR(cp.date), MONTH(cp.date) " +
			"ORDER BY cp.date, cp.value")
	List<CattleTransactionDto> findCattleTransaction(@Param("date") LocalDate date);
}
