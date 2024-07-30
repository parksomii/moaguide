package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.summary.SummaryRepository;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.domain.transaction.TransactionRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SummaryService {
    private final SummaryRepository summaryRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
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
    // 카테고리별 상품 목록
    public List<SummaryCustomDto> getSummaryList(Pageable pageable, String category, String sort) {
        log.info("*************** SummaryService - getSummaryList - category: {}", category);
        // 조회순
        if (sort.equals("views")) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("views").descending());
            Page<Summary> result = summaryRepository.findAllByCategory(category, pageable);
            List<SummaryDto> summaryCustomDtos = result.getContent()
                    .stream()
                    .map(summary -> summary.toDto())
                    .toList();
            List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
            for (SummaryDto summaryDto : summaryCustomDtos) {
                List<Transaction> transactions = transactionRepository.findTwoByProductId(summaryDto.getProductId(), pageable);
                Divide divides = divideService.findByproductId(summaryDto.getProductId());
                if (divides == null) {
                    summaryListDtos.add(new SummaryCustomDto(transactions));
                } else {
                    summaryListDtos.add(new SummaryCustomDto(transactions, divides));
                }
            }
            return summaryListDtos;
        } else
        // 가나다순
        if (sort.equals("name")) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("name").ascending());
            Page<Summary> result = summaryRepository.findAllByCategory(category, pageable);
            List<SummaryDto> summaryCustomDtos = result.getContent()
                    .stream()
                    .map(summary -> summary.toDto())
                    .toList();
            List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
            for (SummaryDto summaryDto : summaryCustomDtos) {
                List<Transaction> transactions = transactionRepository.findTwoByProductId(summaryDto.getProductId(), pageable);
                Divide divides = divideService.findByproductId(summaryDto.getProductId());
                if (divides == null) {
                    summaryListDtos.add(new SummaryCustomDto(transactions));
                } else {
                    summaryListDtos.add(new SummaryCustomDto(transactions, divides));
                }
            }
            return summaryListDtos;
        } else
        // 현재가순
        if (sort.equals("price")) {
            log.info("*************** SummaryService - getSummaryList - sort: {}", sort);
            Page<Summary> result = summaryRepository.findAllByCategory(category, pageable);
            List<SummaryDto> summaryCustomDtos = result.getContent()
                    .stream()
                    .map(summary -> summary.toDto())
                    .toList();
            log.info("*************** SummaryService - getSummaryList - summaryCustomDtos: {}", summaryCustomDtos);
            List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
            for (SummaryDto summaryDto : summaryCustomDtos) {
                List<Transaction> transactions = transactionService.findLatestTransactionsByPrice(summaryDto.getProductId(), pageable);
                log.info("*************** SummaryService - getSummaryList - transactions: {}", transactions);
                Divide divides = divideService.findByproductId(summaryDto.getProductId());
                if (divides == null) {
                    summaryListDtos.add(new SummaryCustomDto(transactions));
                } else {
                    summaryListDtos.add(new SummaryCustomDto(transactions, divides));
                }
            }
            log.info("*************** SummaryService - getSummaryList - summaryListDtos: {}", summaryListDtos);
            return summaryListDtos;
        } else {
            // 수익률순
            Page<Summary> result = summaryRepository.findAllByCategory(category, pageable);
            List<SummaryDto> summaryCustomDtos = result.getContent()
                    .stream()
                    .map(summary -> summary.toDto())
                    .toList();
            List<SummaryCustomDto> summaryListDtos = new ArrayList<>();
            for (SummaryDto summaryDto : summaryCustomDtos) {
                List<Transaction> transactions = transactionRepository.findTwoByProductId(summaryDto.getProductId(), pageable);
                Divide divides = divideService.findByproductId(summaryDto.getProductId());
                if (divides == null) {
                    summaryListDtos.add(new SummaryCustomDto(transactions));
                } else {
                    summaryListDtos.add(new SummaryCustomDto(transactions, divides));
                }
            }
            return summaryListDtos;
        }
    }
}
