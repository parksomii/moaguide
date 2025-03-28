package com.moaguide.refactor.product.repository;

import com.moaguide.refactor.product.dto.TransactionDto;
import com.moaguide.refactor.product.entity.History;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

	@Query("SELECT new com.moaguide.refactor.product.dto.TransactionDto(h.tradeDay,h.price) FROM History h WHERE h.productId.productId = :productId AND h.tradeDay >= :day order by h.tradeDay desc")
	List<TransactionDto> findbyallday(@Param("productId") String productId,
		@Param("day") LocalDate localDate);
}
