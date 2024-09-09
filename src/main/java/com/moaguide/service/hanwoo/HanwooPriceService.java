package com.moaguide.service.hanwoo;

import com.moaguide.domain.hanwoo.AveragePriceRepository;
import com.moaguide.domain.hanwoo.Grade1RateRepository;
import com.moaguide.domain.hanwoo.ProductionCostRepository;
import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import com.moaguide.dto.NewDto.customDto.Grade1RateDto;
import com.moaguide.dto.NewDto.customDto.ProductionCostDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class HanwooPriceService {
    private final AveragePriceRepository averagePriceRepository;
    private final Grade1RateRepository grade1RateRepository;
    private final ProductionCostRepository productionCostRepository;

    // 두당 평균 도매가격
    public List<AveragePriceDto> findAveragePrice(String category) {
//        List<AveragePriceDto> averagePrice = averagePriceRepository.findAveragePrice(category, Year.now().getValue());
        return null;
    }

    // 거세우 평균가격
    public List<AveragePriceDto> findAverageCattlePrice(String category) {
//        List<AveragePriceDto> averagePrice = averagePriceRepository.findAverageCattlePrice(category, Year.now().getValue());
        return null;
    }

    public List<Grade1RateDto> findGrade1Rate(String category) {
//        List<Grade1RateDto> grade1Rate = grade1RateRepository.findGrade1Rate(category);
        return null;
    }

    public List<ProductionCostDto> findProductionCost(String category) {
//        List<ProductionCostDto> productionCost = productionCostRepository.findProductionCost(category);
        return null;
    }
}
