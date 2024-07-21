package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.subway.SubwayTimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@AllArgsConstructor
@Service
public class SubwayTimeService {
    private final SubwayTimeRepository subwayTimeRepository;

    public SubwayTime findBase(String keyword,LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        SubwayTime subwayTime = subwayTimeRepository.findByLastmonth(keyword, 2024, 04);
        return subwayTime;
    }

    public SubwayTime findbydate(String keyword, Integer year, Integer month) {
        SubwayTime subwayTime = subwayTimeRepository.findByLastmonth(keyword, year, month);
        return subwayTime;
    }
}
