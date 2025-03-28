package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.Grade1RateDto;
import com.moaguide.refactor.cow.entity.Grade1Rate;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Grade1RateRepository extends JpaRepository<Grade1Rate, Long> {

	@Query("SELECT new com.moaguide.refactor.cow.dto.Grade1RateDto(g.prdDe, SUM(g.prdDt)) " +
		"FROM Grade1Rate g " +
		"WHERE g.prdDe >= :year AND g.cType IN ('1++', '1+', '1') " +
		"GROUP BY YEAR(g.prdDe), MONTH(g.prdDe) " +
		"ORDER BY YEAR(g.prdDe), MONTH(g.prdDe) DESC")
	List<Grade1RateDto> findGrade1Rate(@Param("year") LocalDate year);

}
