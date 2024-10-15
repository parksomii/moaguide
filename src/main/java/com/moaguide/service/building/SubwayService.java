package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayDayRepository;
import com.moaguide.domain.building.subway.SubwayMonth;
import com.moaguide.domain.building.subway.SubwayMonthRepository;
import com.moaguide.dto.NewDto.BuildingDto.SubwayDto;
import com.moaguide.dto.NewDto.BuildingDto.SubwayTimeDto;
import com.moaguide.dto.NewDto.BuildingSubwayResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SubwayService {
    private final SubwayDayRepository subwayDayRepository;
    private final SubwayMonthRepository subwayMonthRepository;

    @Transactional
    public BuildingSubwayResponseDto findByProductId(String productId) {
        List<SubwayDto> SubwayDay = subwayDayRepository.callSubwayDayProcedure(productId);
        List<SubwayDto> SubwayMonth = subwayMonthRepository.callSubwayMonthProcedure(productId);
        return new BuildingSubwayResponseDto(SubwayDay,SubwayMonth);
    }
}
