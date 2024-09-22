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

    public List<DivideCustomDto> getAllProductIdByDate(String id, String date) {
        // 지급주기별 저작권료 & 시가저작권료
        // (일주일, 6개월, 1년, 전체
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (date.equals("1w")) {
            day = day.minusWeeks(1); // 1주일 전
        } else if (date.equals("6m")) {
            day = day.minusMonths(6); // 6개월 전
        } else if (date.equals("12m")) {
            day = day.minusYears(1); // 1년 전
        } else if (date.equals("all")) {
            day = LocalDate.of(1900, 1, 1); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }

        // LocalDate -> java.sql.Date 변환
        Date daySql = Date.valueOf(day);

        List<DivideCustomDto> divideList = divideRepository.findAllByDate(id, daySql);
        log.info("*************** 배당금 리스트: {}", divideList);
        return divideList;
    }

}
