package com.moaguide.service;

import com.moaguide.domain.divide.CurrentDivideRepository;
import com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CurrentDivideService {
    private final CurrentDivideRepository currentDivideRepository;

    public Integer findCycle(String productId) {
        Integer cycle = currentDivideRepository.findCycle(productId);
        return cycle;
    }

    public List<SummaryDivideCustomDto> findrecent(Pageable pageable) {
        return currentDivideRepository.findrecent(pageable);
    }
}
