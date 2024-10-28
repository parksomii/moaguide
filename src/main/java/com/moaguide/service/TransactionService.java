package com.moaguide.service;

import com.moaguide.domain.history.HistoryRepository;
import com.moaguide.domain.transaction.TransactionRepository;
import com.moaguide.dto.NewDto.DetailTransactionResponseDto;
import com.moaguide.dto.NewDto.customDto.TransactionDto;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final HistoryRepository historyRepository;
    private final EntityManager entityManager;

    public DetailTransactionResponseDto findbymonth(String productId, int month) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        if(month<=12) {

            LocalDate day = LocalDate.now().minusMonths(month);
            // 쿼리를 작성하여 EntityManager를 통해 데이터를 가져옵니다.
            List<Object[]> results = entityManager.createNativeQuery(
                            "SELECT t.trade_day, t.price FROM Transaction t WHERE t.product_id = ?1 AND t.trade_day >= ?2  order by t.trade_day desc ")
                    .setParameter(1, productId)  // 매개변수 이름 일치
                    .setParameter(2, day)  // 변수 day 사용
                    .getResultList();
            if(results.size()>0) {

                long maxValue = (Long)results.get(0)[1];
                long minValue = (Long)results.get(0)[1];

                for (Object[] result : results) {
                    LocalDate tradeDay = ((Date) result[0]).toLocalDate();
                    long price = ((Number) result[1]).longValue();

                    TransactionDto dto = new TransactionDto(tradeDay, price);
                    transactionDtos.add(dto);

                    // 최대값과 최소값 계산
                    if (price > maxValue) {
                        maxValue = price;
                    }
                    if (price < minValue) {
                        minValue = price;
                    }
                }


                return new DetailTransactionResponseDto(transactionDtos, maxValue, minValue);
            }
        }else if(month==100) {
            LocalDate day = LocalDate.now().minusMonths(month);

            // 쿼리를 작성하여 EntityManager를 통해 데이터를 가져옵니다.
            List<Object[]> results = entityManager.createNativeQuery(
                            "SELECT t.trade_day, t.price FROM Transaction t WHERE t.product_id = ?1 AND t.trade_day >= ?2  order by t.trade_day desc")
                    .setParameter(1, productId)  // 매개변수 이름 일치
                    .setParameter(2, day)  // 변수 day 사용
                    .getResultList();
            if(results.size()>0) {
                long maxValue = (Long)results.get(0)[1];
                long minValue = (Long)results.get(0)[1];

                for (Object[] result : results) {
                    LocalDate tradeDay = ((Date) result[0]).toLocalDate();
                    long price = ((Number) result[1]).longValue();

                    TransactionDto dto = new TransactionDto(tradeDay, price);
                    transactionDtos.add(dto);

                    // 최대값과 최소값 계산
                    if (price > maxValue) {
                        maxValue = price;
                    }
                    if (price < minValue) {
                        minValue = price;
                    }
                }

                // 히스토리 테이블에서 데이터를 가져옵니다.
                List<Object[]> historyResults = entityManager.createNativeQuery(
                                "SELECT h.trade_day, h.price FROM History h WHERE h.product_id = ?1 AND h.trade_day >= ?2  order by h.trade_day desc")
                        .setParameter(1, productId)
                        .setParameter(2, day)
                        .getResultList();
                if(historyResults.size()>0) {

                    // 히스토리 테이블의 결과를 TransactionDto로 변환하고 병합
                    for (Object[] result : historyResults) {
                        LocalDate tradeDay = ((Date) result[0]).toLocalDate();
                        long price = ((Number) result[1]).longValue();

                        TransactionDto dto = new TransactionDto(tradeDay, price);
                        transactionDtos.add(dto);

                        // 최대값과 최소값 계산
                        if (price > maxValue) {
                            maxValue = price;
                        }
                        if (price < minValue) {
                            minValue = price;
                        }
                    }

                }
                return new DetailTransactionResponseDto(transactionDtos, maxValue, minValue);
            }
        }
        return new DetailTransactionResponseDto(null,0,0);
    }
}