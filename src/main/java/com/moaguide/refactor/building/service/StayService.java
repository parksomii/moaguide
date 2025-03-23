package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.stay.StayDayRepository;
import com.moaguide.refactor.building.repository.stay.StayRateRepository;
import com.moaguide.dto.NewDto.BuildingDto.StayDayDto;
import com.moaguide.dto.NewDto.BuildingDto.StayRateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StayService {
    private final StayDayRepository stayDayRepository;
    private final StayRateRepository stayrateRepository;

    public List<StayDayDto> findbykeyword(String keyword, int syear, int eyear) {
        return stayDayRepository.findByKeywordandyear(keyword,syear,eyear);
    }

    public List<StayRateDto> findRateBykeyword(String keyword, int syear, int eyear) {
        return stayrateRepository.findByKeywordandyear(keyword,syear,eyear);
    }
}
