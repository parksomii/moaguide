package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.subway.SubwayWeek;
import com.moaguide.domain.building.subway.SubwayWeekRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SubwayWeekService {
    private final SubwayWeekRepository subwayWeekRepository;

    public List<SubwayWeek> findBase(String keyword, LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        List<SubwayWeek> subwayWeek = subwayWeekRepository.findByLastmonth(keyword, 2024, 4);
        log.info("한주단위 data for keyword '{}' in April 2024: {}", keyword, subwayWeek);
        return subwayWeek;
    }

    public List<SubwayWeek> findbydate(String keyword, Integer year, Integer month) {
        List<SubwayWeek> subwayWeek = subwayWeekRepository.findByLastmonth(keyword, year, month);
        return subwayWeek;
    }
}
