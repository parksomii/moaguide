package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.summary.SummaryRepository;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.*;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SummaryService {
    private final SummaryRepository summaryRepository;
    private final TransactionService transactionService;
    private final DivideService divideService;
    private final SummaryListService summaryListService;

    // SummaryController
    // 전체 조회
    public Page<Summary> findAll(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("productId").descending());
        Page<Summary> summaryDtoList = summaryRepository.findAll(pageable);
        log.info("summaryAll : {}", summaryDtoList);
        log.info("summaryID : {}", summaryDtoList.get());
        return summaryDtoList;
    }

    // CategoryController
    // 카테고리 전체 상품 목록 조회
    public PageResponseDTO findAllByCategory(PageRequestDTO pageRequestDTO) {
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("productId").descending());

        // Summary 데이터 페이징 조회
        Page<Summary> allCategory = summaryRepository.findAll(pageable);
        log.info("*************** SummaryService - findAllByCategory - allCategory : {}", allCategory);

        // SummaryDto 변환
        List<SummaryDto> summaryDtoList = allCategory.getContent()
                .stream()
                .map(Summary::toDto)
                .collect(Collectors.toList());
        log.info("*************** SummaryService - summaryDtoList: {}", summaryDtoList);

        // SummaryDto 리스트를 이용하여 추가 데이터 조회 및 설정
        List<Transaction> transactions = transactionService.findAllByid(summaryDtoList);
        List<Divide> divides = divideService.findByID(summaryDtoList);
        List<List<Transaction>> graph = transactionService.graph(summaryDtoList);

        // SummaryListDto 생성
        List<SummaryListDto> summaryListDtos = summaryListService.getSummaryListDto(transactions, divides, graph);
        log.info("*************** SummaryController GET /summaryListDtos: {}", summaryListDtos);

        // PageResponseDTO 생성
        PageResponseDTO pageResponseDTO = new PageResponseDTO(summaryListDtos, pageRequestDTO, allCategory.getTotalElements());
        log.info("*************** SummaryService - pageResponseDTO: {}", pageResponseDTO);

        return pageResponseDTO;
    }

    // 카테고리별 상품 목록 조회
    public PageResponseDTO findByCategory(String category, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("productId").descending());
        Page<Summary> allCategory = summaryRepository.findByCategory(category, pageable);
        log.info("*************** SummaryService - findByCategory - result : {}", allCategory);

        // SummaryDto 변환
        List<SummaryDto> summaryDtoList = allCategory.getContent()
                .stream()
                .map(Summary::toDto)
                .collect(Collectors.toList());
        log.info("*************** SummaryService - summaryDtoList: {}", summaryDtoList);

        // SummaryDto 리스트를 이용하여 추가 데이터 조회 및 설정
        List<Transaction> transactions = transactionService.findAllByid(summaryDtoList);
        List<Divide> divides = divideService.findByID(summaryDtoList);
        List<List<Transaction>> graph = transactionService.graph(summaryDtoList);

        // SummaryListDto 생성
        List<SummaryListDto> summaryListDtos = summaryListService.getSummaryListDto(transactions, divides, graph);
        log.info("*************** SummaryService - summaryListDtos: {}", summaryListDtos);

        // PageResponseDTO 생성
        PageResponseDTO pageResponseDTO = new PageResponseDTO(summaryListDtos, pageRequestDTO, allCategory.getTotalElements());
        log.info("*************** SummaryService - pageResponseDTO: {}", pageResponseDTO);

        return pageResponseDTO;
    }

    public Summary findById(String id) {
        Summary summary = summaryRepository.findByProductId(id);
        return summary;
    }

    public List<Summary> findByViews() {
        Pageable pageable = PageRequest.of(0,2);
        List<Summary> summaries = summaryRepository.findTop2ByOrderByViewsDesc(pageable);
        return summaries;
    }

    public Page<Summary> findlistByCategory(String category, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("productId").descending());
        Page<Summary> summaryDtoList = summaryRepository.findAllByCategory(category,pageable);
        log.info("summaryAll : {}", summaryDtoList);
        log.info("summaryID : {}", summaryDtoList.get());
        return summaryDtoList;
    }


    // 카테고리별 상품현황
    public List<SummaryCustomDto> getSummary(Pageable pageable, String category) {
        Page<Summary> result = summaryRepository.findCategoryBySummary(category, pageable);
        List<SummaryDto> summaryCustomDtos = result.getContent()
                .stream()
                .map(summary -> summary.toDto())
                .toList();
        List<Transaction> transactions = transactionService.findAllByid(summaryCustomDtos);
        List<Divide> divides = divideService.findByID(summaryCustomDtos);
        List<SummaryCustomDto> summaryListDtos = summaryListService.getSummaryCustomDto(transactions, divides);
        return summaryListDtos;
    }
}
