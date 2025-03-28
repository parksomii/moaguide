package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.AveragePriceDto;
import com.moaguide.refactor.cow.entity.AveragePrice;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AveragePriceRepository extends JpaRepository<AveragePrice, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.AveragePriceDto(a.prdDe, AVG(a.prdDt)) " +
		"FROM AveragePrice a " +
		"WHERE a.cType = :category AND a.prdDe >= :year " +
		"GROUP BY YEAR(a.prdDe), MONTH(a.prdDe) " +
		"ORDER BY YEAR(a.prdDe), MONTH(a.prdDe)")
	List<AveragePriceDto> findAveragePrice(@Param("category") String category,
		@Param("year") LocalDate year);
}