package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.Grade1RateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Grade1RateRepository extends JpaRepository<Grade1Rate, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.Grade1RateDto(MONTH(g.prdDe), AVG(g.prdDt), g.cType) " +
            "FROM Grade1Rate g " +
            "WHERE YEAR(g.prdDe) = :year " +
            "GROUP BY MONTH(g.prdDe), g.cType")
    List<Grade1RateDto> findGrade1Rate(@Param("year") int year);

}

