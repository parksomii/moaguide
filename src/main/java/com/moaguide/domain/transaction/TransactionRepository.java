package com.moaguide.domain.transaction;

import com.moaguide.dto.TransactionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.productId.productId = :productId ORDER BY t.tradeDay DESC")
    List<Transaction> findFirstByProductId(@Param("productId") String productId, Pageable pageable);

    @Procedure(procedureName = "GetLatestPrices")
    List<Transaction> findAllByProductIdAndTradeDayAfter(@Param("productId") String productId);

    @Query("SELECT t FROM Transaction t WHERE t.productId.productId = :productId order by t.tradeDay desc,t.tradeTime desc")
    List<Transaction> findTwoByProductId(@Param("productId")String id,Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.productId.productId = :productId AND t.tradeDay >= :day order by t.tradeDay,t.tradeTime")
    List<Transaction> findbyday(@Param("productId") String productId, @Param("day")LocalDate date);

    /*
    // Pageable을 사용하여 첫 번째 Transaction만 가져오기
    @Query("SELECT t FROM Transaction t WHERE t.productId.productId = :productId ORDER BY t.tradeDay DESC")
    List<Transaction> findFirstByProductId(@Param("productId") String productId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.productId.productId = :productId AND t.tradeDay >= :minusone ORDER BY t.tradeDay DESC")
    List<Transaction> findAllByProductIdAndTradeDayAfter(@Param("productId") String productId, @Param("minusone") LocalDate minusone);
}
*/
}