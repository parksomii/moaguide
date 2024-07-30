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

    @Procedure(procedureName = "oneDIvide")
    Divide findByProductId(@Param("productId") String productId);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.DivideCustomDto(d.decisionDay,d.paymentDate,d.dividend,d.dividendRate) FROM Divide d WHERE d.productId.productId = :productId ORDER BY d.decisionDay DESC")
    List<DivideCustomDto> findAllById(@Param("productId") String productId);

    @Procedure(procedureName = "dividegetlast")
    Divide findlast(@Param("productId") String productId);

    @Query("SELECT d FROM Divide d where d.decisionDay>=:DecisionDay ORDER BY d.dividendRate DESC")
    List<Divide> findALLByDividendRate(@Param("DecisionDay") Date decisionDay, Pageable pageable);

    @Query("SELECT d FROM Divide d ORDER BY d.decisionDay DESC")
    List<Divide> findTop2(Pageable pageable);

    @Query("SELECT d FROM Divide d WHERE d.productId.productId = :productId ORDER BY d.decisionDay DESC")
    List<Divide> findlastByPrductId(@Param("productId") String productId, Pageable pageable);

    @Query("SELECT d FROM Divide d WHERE d.productId.productId = :productId AND d.decisionDay>:date")
    List<Divide> findAllByProductIdANDDAY(@Param("productId")String id,@Param("date") Date date);

    @Query("SELECT d FROM Divide d " +
            "WHERE d.decisionDay >= :specificDate AND " +
            "d.productId.PlatformId.category = :category AND "+
            "d.decisionDay = (SELECT MAX(d2.decisionDay) FROM Divide d2 WHERE d2.productId = d.productId AND d2.decisionDay > :specificDate) " +
            "ORDER BY d.dividendRate DESC")
    Page<Divide> findLatestByProductIdAfterDate(@Param("specificDate") Date specificDate, Pageable pageable, @Param("category") String category);

}
