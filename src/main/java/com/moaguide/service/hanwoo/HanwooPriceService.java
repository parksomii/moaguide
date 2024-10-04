package com.moaguide.service.hanwoo;

import com.moaguide.domain.hanwoo.*;
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

    public List<?> getHanwooPriceData(String category, int month) {
        LocalDate startDate = LocalDate.now();

        // 날짜 파라미터에 따라 startDate 설정
        if (month == 1) {
            startDate = startDate.minusYears(month);  // 1년 전
        } else if (month == 3) {
            startDate = startDate.minusYears(month);  // 3년 전
        } else if (month == 5) {
            startDate = startDate.minusYears(month);  // 5년 전
        } else if (month == 100) {
            startDate = startDate.minusYears(month);  // 전체 데이터
        } else {
            return null;  // 유효하지 않은 날짜 파라미터 처리
        }


        if ("grade1Rate".equals(category)) {
            List<Grade1RateDto> grade1Rate = grade1RateRepository.findGrade1Rate(startDate);
            return grade1Rate;
        } else if ("productionCost".equals(category)) {
            int newStartYear = startDate.getYear();  // 연도로 변환
            List<ProductionCostDto> productionCost = productionCostRepository.findProductionCost(newStartYear); // 2003년부터 2023년까지의 데이터
            return productionCost;
        } else if ("averagePrice".equals(category)) {
            List<AveragePriceDto> averagePrice = averagePriceRepository.findAveragePrice("한우", startDate);
            return averagePrice;
        } else if ("cattlePrice".equals(category)) {
            List<AveragePriceDto> averagePrice = averagePriceRepository.findAveragePrice("거세", startDate);
            return averagePrice;
        } else {
            return null;
        }
    }

    public List<?> findHanwooMarket(String category, int month) {
        LocalDate startDate = LocalDate.now();

        if (month == 1) {
            startDate = startDate.minusYears(month);  // 1년 전
        } else if (month == 3) {
            startDate = startDate.minusYears(month);  // 3년 전
        } else if (month == 5) {
            startDate = startDate.minusYears(month);  // 5년 전
        } else if (month == 100) {
            startDate = startDate.minusYears(month);  // 전체 데이터
        } else {
            return null;  // 유효하지 않은 날짜 파라미터 처리
        }

        if ("cattlePopulation".equals(category)) {
            List<CattlePopulationDto> cattlePopulation = cattlePopulationRepository.findCattlePopulation(startDate);
            return cattlePopulation;
        } else if ("cattleSale".equals(category)) {
            List<CattleSaleDto> cattleSale = cattleSaleRepository.findCattleSale(startDate);
            return cattleSale;
        } else if ("cattleFarm".equals(category)) {
            List<CattleFarmDto> cattleFarm = cattleFarmRepository.findCattleFarm(startDate);
            return cattleFarm;
        } else if ("cattleTransaction".equals(category)) {
            List<CattleTransactionDto> cattleTransaction = cattlePriceRepository.findCattleTransaction(startDate);
            return cattleTransaction;
        } else {
            return null;
        }
    }
}
