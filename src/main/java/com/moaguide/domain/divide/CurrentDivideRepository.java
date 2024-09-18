package com.moaguide.domain.divide;

import com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentDivideRepository extends JpaRepository<CurrentDivide, Long> {

    @Query("SELECT cd.divideCycle FROM CurrentDivide cd where cd.productId.productId = :product_Id")
    Integer findCycle(@Param("product_Id") String productId);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto(p.productId,pl.category,p.name,cd.dividend) FROM CurrentDivide cd,Product p,Platform pl where cd.productId.productId = p.productId and pl.PlatformId = p.PlatformId.PlatformId order by  cd.paymentDate desc")
    List<SummaryDivideCustomDto> findrecent(Pageable pageable);
}

