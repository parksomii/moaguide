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

}
