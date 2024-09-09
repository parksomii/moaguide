package com.moaguide.domain.divide;

import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DivideRepository extends JpaRepository<Divide, Long> {
    @Query("SELECT d FROM Divide d WHERE d.productId.productId = :productId ORDER BY d.decisionDay DESC")
    List<Divide> findAllByProductId(@Param("productId") String productId);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.DivideCustomDto(d.decisionDay,d.paymentDate,d.dividend,d.dividendRate) FROM Divide d WHERE d.productId.productId = :productId ORDER BY d.decisionDay DESC")
    List<DivideCustomDto> findAllById(@Param("productId") String productId);

    @Procedure(procedureName = "dividegetlast")
    Divide findlast(@Param("productId") String productId);

}
