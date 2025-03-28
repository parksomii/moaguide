package com.moaguide.refactor.product.repository;

import com.moaguide.refactor.product.dto.DivideCustomDto;
import com.moaguide.refactor.product.entity.Divide;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface DivideRepository extends JpaRepository<Divide, Long> {

	// 배당금 리스트 조회
	@Query(
		"SELECT new com.moaguide.refactor.product.dto.DivideCustomDto(d.paymentDate,d.dividend,d.dividendRate) "
			+
			"FROM Divide d WHERE d.productId.productId = :productId AND d.decisionDay >= :day ORDER BY d.decisionDay")
	List<DivideCustomDto> findAllByDate(@Param("productId") String productId,
		@Param("day") Date day);

	@Procedure(procedureName = "dividegetlast")
	Divide findlast(@Param("productId") String productId);

}
