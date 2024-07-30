package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.summary.SummaryRepository;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.domain.transaction.TransactionRepository;
import com.moaguide.dto.*;
import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SummaryService {
    private final SummaryRepository summaryRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final DivideRepository divideRepository;
    private final SummaryListService summaryListService;


    public Summary findById(String id) {
        Summary summary = summaryRepository.findByProductId(id);
        return summary;
    }

    // 카테고리별 상품현황
    public List<SummaryCustomDto> getSummary(Pageable pageable, String category) {
        Page<Summary> result = summaryRepository.findAllByCategory(category, pageable);
        List<SummaryDto> summaryCustomDtos = result.getContent()
                .stream()
                .map(summary -> summary.toDto())
                .toList();
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
            pageable = PageRequest.of(0, 2);
        for(SummaryDto summaryDto : summaryCustomDtos) {
            List<Transaction> transactions = transactionRepository.findTwoByProductId(summaryDto.getProductId(), pageable);
            Divide divides = divideService.findByproductId(summaryDto.getProductId());
            if(divides == null) {
                summaryListDtos.add(new SummaryCustomDto(transactions));
            } else {
                summaryListDtos.add(new SummaryCustomDto(transactions, divides));
            }
        }

        return summaryListDtos;
    }

    public List<SummaryCustomDto> getSummaryDvide(int page,int size, String category) {
        Pageable pageable = PageRequest.of(page, size);
        Date date = Date.valueOf(LocalDate.now().minusMonths(6).with(TemporalAdjusters.firstDayOfMonth()));
        Page<Divide> divides = divideRepository.findLatestByProductIdAfterDate(date,pageable,category);
        List<SummaryCustomDto> summaryCustomDtos = new ArrayList<>();
        for(Divide divide : divides.getContent()) {
            List<Transaction> transactions = transactionRepository.findAllByProductIdAndTradeDayAfter(divide.getProductId());
            summaryCustomDtos.add(new SummaryCustomDto(transactions,divide));
        }
        return summaryCustomDtos;
    }

    // 카테고리별 상품 목록
    public List<SummaryCustomDto> getSummaryView(int page,int size, String category) {
        List<IdDto> findViews = summaryRepository.findListByCategory(category, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "views")));
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
        for(IdDto idDto : findViews) {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());
            Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());
            if (findDivide != null) {
                summaryListDtos.add(new SummaryCustomDto(transactionList, findDivide));
            } else {
                summaryListDtos.add(new SummaryCustomDto(transactionList));
            }
        }
        return summaryListDtos;
    }

    public List<SummaryCustomDto> getSummaryName(int page, int size, String category) {

    }

}
