package com.moaguide.refactor.contents.repository;

import com.moaguide.refactor.contents.dto.ContentDetailDto;
import com.moaguide.refactor.contents.dto.ContentInvestmentDto;
import com.moaguide.refactor.contents.dto.ContentPublishDto;
import com.moaguide.refactor.contents.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	@Procedure(name = "ContentDetailProcedure")
	ContentDetailDto findByDetail(@Param("in_Id") String in_Id, @Param("nickname") String nickname);

	@Query("select new com.moaguide.refactor.contents.dto.ContentInvestmentDto(c.totalBudget,c.customerUnitPrice,c.profitLossRatio,c.breakEvenPoint) FROM Content c where c.productId.productId = :id")
	ContentInvestmentDto findInvest(@Param("id") String productId);

	@Procedure(name = "ContentPublishProcedure")
	ContentPublishDto findPublish(@Param("pro_Id") String pro_Id);

}
