package com.moaguide.service.hanwoo;

import com.moaguide.domain.hanwoo.*;
import com.moaguide.dto.NewDto.HanwooMarketResponseDto;
import com.moaguide.dto.NewDto.HanwooPriceResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class HanwooPriceService {
    private final AveragePriceRepository averagePriceRepository;
    private final Grade1RateRepository grade1RateRepository;
    private final ProductionCostRepository productionCostRepository;
    private final CattlePopulationRepository cattlePopulationRepository;
    private final CattleSaleRepository cattleSaleRepository;
    private final CattleFarmRepository cattleFarmRepository;
    private final CattlePriceRepository cattlePriceRepository;

    public HanwooPriceResponseDto getHanwooPriceData(String category, String date) {
        LocalDate startDate = LocalDate.now();

        // 날짜 파라미터에 따라 startDate 설정
        if ("1y".equals(date)) {
            startDate = startDate.minusYears(1);  // 1년 전
        } else if ("3y".equals(date)) {
            startDate = startDate.minusYears(3);  // 3년 전
        } else if ("5y".equals(date)) {
            startDate = startDate.minusYears(5);  // 5년 전
        } else if ("all".equals(date)) {
            startDate = LocalDate.of(1990, 1, 1);  // 전체 데이터
        } else {
            return null;  // 유효하지 않은 날짜 파라미터 처리
        }

        List<AveragePriceDto> averagePrice = null;
        List<Grade1RateDto> grade1Rate = null;
        List<ProductionCostDto> productionCost = null;

        if ("grade1Rate".equals(category)) {
            grade1Rate = grade1RateRepository.findGrade1Rate(startDate);
        } else if ("productionCost".equals(category)) {
            int newStartYear = startDate.getYear();  // 연도로 변환
            productionCost = productionCostRepository.findProductionCost(newStartYear); // 2003년부터 2023년까지의 데이터
        } else if ("averagePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("한우", startDate);
        } else if ("cattlePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("거세", startDate);
        } else {
            return null;
        }

        return new HanwooPriceResponseDto(averagePrice, grade1Rate, productionCost);
    }

    public HanwooMarketResponseDto findHanwooMarket(String category, String date) {
        LocalDate startDate = LocalDate.now();

        if ("1y".equals(date)) {
            startDate = startDate.minusYears(1);  // 1년 전
        } else if ("3y".equals(date)) {
            startDate = startDate.minusYears(3);  // 3년 전
        } else if ("5y".equals(date)) {
            startDate = startDate.minusYears(5);  // 5년 전
        } else if ("all".equals(date)) {
            startDate = LocalDate.of(1990, 1, 1);  // 전체 데이터
        } else {
            return null;  // 유효하지 않은 날짜 파라미터 처리
        }

        List<CattlePopulationDto> cattlePopulation = null;
        List<CattleSaleDto> cattleSale = null;
        List<CattleFarmDto> cattleFarm = null;
        List<CattleTransactionDto> cattleTransaction = null;

        if ("cattlePopulation".equals(category)) {
            cattlePopulation = cattlePopulationRepository.findCattlePopulation(startDate);
        } else if ("cattleSale".equals(category)) {
            cattleSale = cattleSaleRepository.findCattleSale(startDate);
        } else if ("cattleFarm".equals(category)) {
            cattleFarm = cattleFarmRepository.findCattleFarm(startDate);
        } else if ("cattleTransaction".equals(category)) {
            cattleTransaction = cattlePriceRepository.findCattleTransaction(startDate);
        } else {
            return null;
        }

        return new HanwooMarketResponseDto(cattlePopulation, cattleSale, cattleFarm, cattleTransaction);
    }
}
