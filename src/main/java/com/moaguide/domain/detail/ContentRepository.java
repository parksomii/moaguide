package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentInvestmentDto;
import com.moaguide.dto.NewDto.customDto.ContentPublishDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Procedure(name = "ContentDetailProcedure")
    ContentDetailDto findByDetail(@Param("in_Id") String in_Id);

    @Procedure(name = "ContentPublishProcedure")
    ContentPublishDto findpublish(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.ContentInvestmentDto(c.totalBudget,c.customerUnitPrice,c.profitLossRatio,c.breakEvenPoint) FROM Content c where c.productId.productId = :id")
    ContentInvestmentDto findInvest(@Param("id") String productId);
}
