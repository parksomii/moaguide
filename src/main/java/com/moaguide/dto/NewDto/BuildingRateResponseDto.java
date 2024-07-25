package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuildingRateResponseDto {
    private List<RentDto> rent;
    private List<VacancyrateDto> vacancyrate;
}
