package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayMonthRepository;
import com.moaguide.dto.NewDto.BuildingDto.SubwayWeekDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SubwayWeekService {
    private final SubwayMonthRepository subwayMonthRepository;

    public List<SubwayWeekDto> findbydate(String productId, Integer year, Integer month) {
        List<SubwayWeekDto> subwayWeek = subwayMonthRepository.findByLastmonth(productId, year, month);
        return subwayWeek;
    }
}
