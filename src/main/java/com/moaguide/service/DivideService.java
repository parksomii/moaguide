package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import com.moaguide.dto.NewDto.customDto.TransactionDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DivideService {
    private final DivideRepository divideRepository;


    // 지급주기별 저작권료 & 시가저작권료 조회
    public List<DivideCustomDto> getAllProductIdByDate(String id, int month) {

        List<DivideCustomDto> divideList = new ArrayList<>();
        if(month<=12) {
            divideList= divideRepository.findAllByDate(id, Date.valueOf(LocalDate.now().minusMonths(month)));
        }else if(month==100) {
            divideList = divideRepository.findAllByDate(id, Date.valueOf(LocalDate.now().minusMonths(month)));
        }else {
            divideList = null;
        }
        return divideList;
    }
}
