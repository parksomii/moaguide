package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import com.moaguide.dto.SummaryDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DivideService {
    private final DivideRepository divideRepository;

    public List<Divide> findByID(List<SummaryDto> summaryDtoList) {
        log.info("*************** DivideService findByID");
        log.info("*************** DivideService findByID - summaryList : {}", summaryDtoList);
        List<Divide> divides = new ArrayList<>();
        for(SummaryDto summary : summaryDtoList){
            log.info("*************** DivideService findByID - summary : {}", summary);
            String productId = summary.getProductId();
            log.info("*************** DivideService findByID - productId : {}", productId);
            divides.addAll(divideRepository.findAllByProductId(productId));
            log.info("*************** DivideService findByID - divides : {}", divides);
        }
        return divides;
    }

/*    @Transactional
    public Divide findByproductId(String id) {
        Divide divide =divideRepository.findByProductId(id);
        return divide;
    }*/

    public List<DivideCustomDto> findAllByproductId(String id) {
        List<DivideCustomDto> divideList = divideRepository.findAllById(id);
        log.info("*************** 배당금 리스트: {}", divideList);
        return divideList;
    }

    @Transactional
    public Divide findOne(String id) {
        Divide divide = divideRepository.findlast(id);
        return divide;
    }

    public List<Divide> topthree() {
        Date date = Date.valueOf(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
        Pageable pageable = PageRequest.of(0, 3);
        List<Divide> divides = divideRepository.findALLByDividendRate(date,pageable);
        return divides;
    }

    public List<Divide> findTwo() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Divide> divides = divideRepository.findTop2(pageable);
        return divides;
    }

    public Divide findlastByPrductId(String productId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Divide> divide = divideRepository.findlastByPrductId(productId,pageable);
        return divide.get(0);
    }

    public List<Divide> threeMonthgraph(String id) {
        Date date = Date.valueOf(LocalDate.now().minusMonths(3).with(TemporalAdjusters.firstDayOfMonth()));
        List<Divide> divide =divideRepository.findAllByProductIdANDDAY(id,date);
        return divide;
    }
}
