package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayWeekRepository;
import com.moaguide.dto.NewDto.BuildingDto.SubwayWeekDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
