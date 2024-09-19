package com.moaguide.domain.content;

import com.moaguide.dto.NewDto.customDto.PerformanceInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository  extends JpaRepository<Performance, Long> {


    @Query("SELECT new com.moaguide.dto.NewDto.customDto.PerformanceInfoDto(p.venue,p.period,p.casting,p.director,p.showTimes,p.producer) FROM Performance p where p.productId.productId=:Id")
    PerformanceInfoDto findByProductId(@Param("Id") String productId);
}
