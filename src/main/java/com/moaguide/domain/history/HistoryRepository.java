package com.moaguide.domain.history;

import com.moaguide.dto.NewDto.customDto.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.TransactionDto(h.tradeDay,h.price) FROM History h WHERE h.productId.productId = :productId AND h.tradeDay >= :day order by h.tradeDay")
    List<TransactionDto> findbyallday(@Param("productId") String productId,@Param("day") LocalDate localDate);
}
