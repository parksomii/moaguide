package com.moaguide.service;

import com.moaguide.domain.history.HistoryRepository;
import com.moaguide.domain.transaction.TransactionRepository;
import com.moaguide.dto.NewDto.customDto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final HistoryRepository historyRepository;

    public List<TransactionDto> findbymonth(String productId, int month) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        if(month<=12) {
            transactionDtos= transactionRepository.findbyday(productId, LocalDate.now().minusMonths(month));
        }else if(month==100) {
            transactionDtos = transactionRepository.findbyday(productId, LocalDate.now().minusMonths(month));
            List<TransactionDto> historyDtos = historyRepository.findbyallday(productId, LocalDate.now().minusMonths(month));
            if (historyDtos != null && !historyDtos.isEmpty()) {
                transactionDtos.addAll(historyDtos);  // 리스트를 이어붙임
            }
        }else {
            transactionDtos = null;
        }
        return transactionDtos;
    }
}