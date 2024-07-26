package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.domain.transaction.TransactionRepository;
import com.moaguide.dto.NewDto.customDto.TransactionDto;
import com.moaguide.dto.SummaryDto;
import com.moaguide.dto.SummaryListDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> findAllByid(List<SummaryDto> summaryList) {
        log.info("*************** TransactionService findAllByid");
        log.info("*************** TransactionService findAllByid - summaryList : {}", summaryList);
        List<Transaction> transactions = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 1); // 첫 번째 결과만 반환
        for (SummaryDto summary : summaryList) {
            String productId = summary.getProductId();
            // 첫 번째 Transaction만 가져오기 위해 Pageable 사용
            List<Transaction> result = transactionRepository.findFirstByProductId(productId, pageable);
            if (!result.isEmpty()) {
                transactions.add(result.get(0)); // 리스트에서 첫 번째 항목을 추가
            }
        }

        log.info("Transactions found: {}", transactions);
        return transactions;
    }

    @Transactional
    public List<List<Transaction>> graph(List<SummaryDto> summaryList) {
        List<List<Transaction>> transactions = new ArrayList<>();
        for (SummaryDto summary : summaryList) {
            String productId = summary.getProductId();
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(summary.getProductId());
            if (!transactionList.isEmpty()) {
                transactions.add(transactionList);
            } else {
                transactions.add(null);
            }
        }
        log.info(transactions.toString());
        return transactions;
    }

    public List<Transaction> findtwoByproductId(String id) {
        Pageable pageable = PageRequest.of(0, 2);
        List<Transaction> transactions = transactionRepository.findTwoByProductId(id,pageable);
        return transactions;
    }

    public Transaction findbyproductId(String id) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Transaction> transaction = transactionRepository.findByProductId(id, pageable);
        return transaction.getContent().get(0); // 첫 번째 Transaction 반환
    }



    public List<TransactionDto> findbymonth(String productId, int month) {
        List<TransactionDto> transactionDtos = transactionRepository.findbyday(productId,LocalDate.now().minusMonths(month));
        return transactionDtos;
    }
}