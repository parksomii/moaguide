package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTimeRepository;
import com.moaguide.dto.NewDto.BuildingDto.SubwayTimeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubwayTimeService {
    private final SubwayTimeRepository subwayTimeRepository;

    public SubwayTimeDto findbydate(String keyword, Integer year, Integer month) {
        SubwayTimeDto subwayTime = subwayTimeRepository.findByLastmonth(keyword, year, month);
        return subwayTime;
    }
}
