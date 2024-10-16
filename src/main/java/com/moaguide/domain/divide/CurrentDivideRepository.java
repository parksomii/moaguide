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

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto(p.productId,pl.category,p.name,round((p.nowPiece/p.piece)*100,2)) FROM Product p,Platform pl,IssuePrice ip where pl.PlatformId = p.PlatformId.PlatformId and pl.status = '공모 중' and ip.id.productId = p.productId order by  ip.day desc")
    List<SummaryDivideCustomDto> findrecent(Pageable pageable);
}

