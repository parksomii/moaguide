package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.ProductionCostDto;
import com.moaguide.refactor.cow.entity.ProductionCost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductionCostRepository extends JpaRepository<ProductionCost, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.ProductionCostDto(p.prdDe, p.prdDt) " +
		"FROM ProductionCost p " +
		"WHERE YEAR(p.prdDe) >= :year " +
		"GROUP BY YEAR(p.prdDe)")
	List<ProductionCostDto> findProductionCost(@Param("year") Integer year);
}
