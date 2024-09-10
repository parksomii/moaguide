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

    public HanwooPriceResponseDto getHanwooPriceData(String category) {
        int currentYear = LocalDate.now().getYear();
        List<AveragePriceDto> averagePrice = null;
        List<Grade1RateDto> grade1Rate = null;
        List<ProductionCostDto> productionCost = null;

        if ("grade1Rate".equals(category)) {
            grade1Rate = grade1RateRepository.findGrade1Rate(currentYear);
        } else if ("productionCost".equals(category)) {
            productionCost = productionCostRepository.findProductionCost(currentYear - 1); // 2023년 데이터
        } else if ("averagePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("한우", currentYear);
        } else if ("cattlePrice".equals(category)) {
            averagePrice = averagePriceRepository.findAveragePrice("거세", currentYear);
        } else {
            return null;
        }

        return new HanwooPriceResponseDto(averagePrice, grade1Rate, productionCost);
    }
}
