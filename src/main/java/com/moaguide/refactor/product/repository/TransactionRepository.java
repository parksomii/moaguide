package com.moaguide.refactor.product.repository;

import com.moaguide.refactor.product.dto.TransactionDto;
import com.moaguide.refactor.product.entity.Transaction;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// 일별 거래내역 조회
	@Query("SELECT new com.moaguide.refactor.product.dto.TransactionDto(t.tradeDay,t.price) FROM Transaction t WHERE t.productId.productId = :productId AND t.tradeDay >= :day order by t.tradeDay desc")
	List<TransactionDto> findbyday(@Param("productId") String productId,
		@Param("day") LocalDate date);

}