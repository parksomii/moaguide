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

    public List<TransactionDto> findbymonth(String productId, int month) {
        List<TransactionDto> transactionDtos = transactionRepository.findbyday(productId,LocalDate.now().minusMonths(month));
        return transactionDtos;
    }
}