package com.moaguide.service.hanwoo;

import com.moaguide.domain.hanwoo.AveragePriceRepository;
import com.moaguide.domain.hanwoo.Grade1RateRepository;
import com.moaguide.domain.hanwoo.ProductionCostRepository;
import com.moaguide.dto.NewDto.HanwooPriceResponseDto;
import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import com.moaguide.dto.NewDto.customDto.Grade1RateDto;
import com.moaguide.dto.NewDto.customDto.ProductionCostDto;
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

    public HanwooPriceResponseDto getHanwooPriceData(String category, int currentYear) {
        LocalDate startDate;
        int nowDate = LocalDate.now().getYear();

        // 연도 파라미터에 따라 startDate 설정
        if (currentYear == 1) {
            startDate = LocalDate.of(nowDate, 1, 1);  // 현재 연도
        } else if (currentYear == 3) {
            startDate = LocalDate.of(nowDate - currentYear, 1, 1);  // 3년 전부터
        } else if (currentYear == 5) {
            startDate = LocalDate.of(nowDate - currentYear, 1, 1);  // 5년 전부터
        } else {
            // 전체 데이터를 가져올 경우
            startDate = LocalDate.of(1990, 1, 1);  // 시작 날짜를 적당히 과거로 설정
        }

        log.info("currentYear: {}", currentYear);
        log.info("startDate: {}", startDate);
        List<AveragePriceDto> averagePrice = null;
        List<Grade1RateDto> grade1Rate = null;
        List<ProductionCostDto> productionCost = null;

        if ("grade1Rate".equals(category)) {
            grade1Rate = grade1RateRepository.findGrade1Rate(startDate);
        } else if ("productionCost".equals(category)) {
            productionCost = productionCostRepository.findProductionCost(startDate); // 2003년부터 2023년까지의 데이터
        } else if ("averagePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("한우", startDate);
        } else if ("cattlePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("거세", startDate);
        } else {
            return null;
        }

        return new HanwooPriceResponseDto(averagePrice, grade1Rate, productionCost);
    }
}
