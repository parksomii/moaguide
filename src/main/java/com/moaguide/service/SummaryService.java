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
import com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryRecentDto;
import jakarta.transaction.Transactional;
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

    // 최근 배당금 발표
    @Transactional
    public List<SummaryDivideCustomDto> getDivide(int page,int size, String category) {
        List<IdDto> findDivide;
        List<SummaryDivideCustomDto> summaryDivideListDtos = new ArrayList<>();
        if(category.equals("all")){
            findDivide = summaryRepository.findAllByList(PageRequest.of(page - 1, size));
        } else {
            findDivide = summaryRepository.findAllByCategory(category, PageRequest.of(page - 1, size));
        }
        for(IdDto idDto : findDivide) {
            Divide findDivide1 = divideRepository.findByProductId(idDto.getProduct_Id());
            if (findDivide1 != null) {
                summaryDivideListDtos.add(new SummaryDivideCustomDto(findDivide1));
            }
        }
        return summaryDivideListDtos;
    }

    // 카테고리별 상품현황
    @Transactional
    public List<SummaryCustomDto> getSummary(int page,int size, String category) {
        List<IdDto> findSummary;
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
        if(category.equals("all")){
            findSummary = summaryRepository.findAllByList(PageRequest.of(page - 1, size));
        } else {
            findSummary = summaryRepository.findAllByCategory(category, PageRequest.of(page - 1, size));
        }
        for (IdDto idDto : findSummary) {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());
            if (!transactionList.isEmpty()) {
                Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());
                if (findDivide == null) {
                    summaryListDtos.add(new SummaryCustomDto(transactionList));
                } else {
                    summaryListDtos.add(new SummaryCustomDto(transactionList, findDivide));
                }
            }else {
                log.warn("섬머리 트랜잭션 리스트가 비어 있음: " + idDto.getProduct_Id());
            }
        }
        return summaryListDtos;
    }

    @Transactional
    public Page<SummaryCustomDto> getSummaryDvide(int page,int size, String category) {
        Pageable pageable = PageRequest.of(page-1, size);
        Date date = Date.valueOf(LocalDate.now().minusMonths(6).with(TemporalAdjusters.firstDayOfMonth()));
        Page<Divide> divides = divideRepository.findLatestByProductIdAfterDate(date,pageable,category);
        /*List<SummaryCustomDto> summaryCustomDtos = new ArrayList<>();
        for(Divide divide : divides.getContent()) {
            List<Transaction> transactions = transactionRepository.findAllByProductIdAndTradeDayAfter(divide.getProductId());
            summaryCustomDtos.add(new SummaryCustomDto(transactions,divide));
        }*/
        Page<SummaryCustomDto> summaryCustomDtos = divides.map(divide -> {
            List<Transaction> transactions = transactionRepository.findAllByProductIdAndTradeDayAfter(divide.getProductId());
            return new SummaryCustomDto(transactions,divide);
        });
        return summaryCustomDtos;
    }

    // 카테고리별 상품 목록
    @Transactional
    public Page<SummaryCustomDto> getSummaryView(int page,int size, String category) {
        log.info("섬머리 카테고리 " + category);
        Page<IdDto> findViews;
        Pageable pageable = PageRequest.of(page - 1, size);
        if (category.equals("all")) {
            findViews = summaryRepository.findListByAllbyViews(pageable);
        } else {
            findViews = summaryRepository.findListByCategory(category, pageable);
        }
        // Page<IdDto>를 Page<SummaryCustomDto>로 변환
        Page<SummaryCustomDto> summaryCustomDtos = findViews.map(idDto -> {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());

            if (!transactionList.isEmpty()) {
                Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());

                if (findDivide == null) {
                    return new SummaryCustomDto(transactionList);
                } else {
                    return new SummaryCustomDto(transactionList, findDivide);
                }
            } else {
                return null;  // transactionList가 비어 있을 때 null 반환
            }
        });
        return summaryCustomDtos;
    }

    @Transactional
    public Page<SummaryCustomDto> getSummaryName(int page, int size, String category) {
        Page<IdDto> findNames;
        Pageable pageable = PageRequest.of(page - 1, size);
        if(category.equals("all")){
            findNames = summaryRepository.findListByAllbyName(PageRequest.of(page - 1, size));
        } else {
            findNames = summaryRepository.findListByCategoryAndName(category, PageRequest.of(page - 1, size));
        }
        // Page<IdDto>를 Page<SummaryCustomDto>로 변환
        Page<SummaryCustomDto> summaryCustomDtos = findNames.map(idDto -> {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());

            if (!transactionList.isEmpty()) {
                Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());

                if (findDivide == null) {
                    return new SummaryCustomDto(transactionList);
                } else {
                    return new SummaryCustomDto(transactionList, findDivide);
                }
            } else {
                return null;  // transactionList가 비어 있을 때 null 반환
            }
        });
        return summaryCustomDtos;
    }
}
