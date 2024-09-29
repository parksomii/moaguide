package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.Grade1RateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface Grade1RateRepository extends JpaRepository<Grade1Rate, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.Grade1RateDto(g.prdDe, SUM(g.prdDt)) " +
            "FROM Grade1Rate g " +
            "WHERE g.prdDe >= :year AND g.cType IN ('1++', '1+', '1') " +
            "GROUP BY YEAR(g.prdDe), MONTH(g.prdDe) " +
            "ORDER BY YEAR(g.prdDe), MONTH(g.prdDe) DESC")
    List<Grade1RateDto> findGrade1Rate(@Param("year") LocalDate year);

}
