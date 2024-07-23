package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.subway.SubwayWeek;
import com.moaguide.domain.building.subway.SubwayWeekRepository;
import com.moaguide.dto.NewDto.SubwayWeekDto;
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

    public List<SubwayWeekDto> findbydate(String keyword, Integer year, Integer month) {
        List<SubwayWeekDto> subwayWeek = subwayWeekRepository.findByLastmonth(keyword, year, month);
        return subwayWeek;
    }
}
