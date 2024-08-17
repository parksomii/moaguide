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
    public List<SummaryDivideCustomDto> getDivide(Pageable pageable, String category) {
        List<IdDto> findDivide = summaryRepository.findAllByCategory(category, pageable);
        List<SummaryDivideCustomDto> summaryDivideListDtos = new ArrayList<>();
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
    public List<SummaryCustomDto> getSummary(Pageable pageable, String category) {
        List<IdDto> findSummary = summaryRepository.findAllByCategory(category, pageable);
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
        for(IdDto idDto : findSummary) {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());
            Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());
            if (findDivide == null) {
                summaryListDtos.add(new SummaryCustomDto(transactionList));
            } else {
                summaryListDtos.add(new SummaryCustomDto(transactionList, findDivide));
            }
        }
        return summaryListDtos;
    }


    @Transactional
    public List<SummaryCustomDto> getSummaryDvide(int page,int size, String category) {
        Pageable pageable = PageRequest.of(page-1, size);
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
    @Transactional
    public List<SummaryCustomDto> getSummaryView(int page,int size, String category) {
        log.info("섬머리 카테고리 " + category);
        List<IdDto> findViews;
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
        log.info("섬머리 리스트 " + summaryListDtos);
        if(category.equals("all")){
            findViews = summaryRepository.findListByAllbyViews(PageRequest.of(page - 1, size));
            log.info("섬머리 all findViews" + findViews);
        } else {
            findViews = summaryRepository.findListByCategory(category, PageRequest.of(page - 1, size));
            log.info("섬머리" + category, "findViews" + findViews);
        }
        for(IdDto idDto : findViews) {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());
            log.info("섬머리 트랜잭션 리스트" + transactionList);
            Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());
            log.info("섬머리 디바이드" + findDivide);
            if (findDivide == null) {
                log.info("섬머리 디바이드 없음");
                summaryListDtos.add(new SummaryCustomDto(transactionList));
                log.info("섬머리 리스트" + summaryListDtos);
            } else {
                log.info("섬머리 디바이드 있음");
                summaryListDtos.add(new SummaryCustomDto(transactionList, findDivide));
                log.info("섬머리 리스트" + summaryListDtos);
            }
        }
        return summaryListDtos;
    }

    @Transactional
    public List<SummaryCustomDto> getSummaryName(int page, int size, String category) {
        List<IdDto> findNames = summaryRepository.findListByCategoryAndName(category, PageRequest.of(page-1, size, Sort.by(Sort.Direction.ASC, "name")));
        List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
        for(IdDto idDto : findNames) {
            List<Transaction> transactionList = transactionRepository.findAllByProductIdAndTradeDayAfter(idDto.getProduct_Id());
            Divide findDivide = divideRepository.findByProductId(idDto.getProduct_Id());
            if (findDivide == null) {
                summaryListDtos.add(new SummaryCustomDto(transactionList));
            } else {
                summaryListDtos.add(new SummaryCustomDto(transactionList, findDivide));
            }
        }
        return summaryListDtos;
    }

}
