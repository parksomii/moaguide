package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayDayRepository;
import com.moaguide.domain.building.subway.SubwayMonthRepository;
import com.moaguide.dto.NewDto.BuildingDto.SubwayDto;
import com.moaguide.dto.NewDto.BuildingSubwayResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class SubwayService {
    private final SubwayDayRepository subwayDayRepository;
    private final SubwayMonthRepository subwayMonthRepository;

    @Transactional
    public BuildingSubwayResponseDto findByProductId(String productId) {
        LocalDate minust1Month= LocalDate.now().minusMonths(1);
        Date date = Date.valueOf(minust1Month);
        List<SubwayDto> SubwayDay = subwayDayRepository.callSubwayDayProcedure(productId,date);
        List<SubwayDto> SubwayMonth = subwayMonthRepository.callSubwayMonthProcedure(productId);
        return new BuildingSubwayResponseDto(SubwayDay,SubwayMonth);
    }
}
