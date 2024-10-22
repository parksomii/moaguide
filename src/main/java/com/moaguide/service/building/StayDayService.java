package com.moaguide.service.building;

import com.moaguide.domain.building.stayday.StayDayRepository;
import com.moaguide.dto.NewDto.BuildingDto.StayDayDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StayDayService {
    private final StayDayRepository stayDayRepository;

    public List<StayDayDto> findbykeyword(String keyword, int syear, int eyear) {
        return stayDayRepository.findByKeywordandyear(keyword,syear,eyear);
    }
}
