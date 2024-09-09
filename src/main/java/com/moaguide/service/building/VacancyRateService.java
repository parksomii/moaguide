package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.vacancyrate.VacancyRate;
import com.moaguide.domain.building.vacancyrate.VacancyRateRepository;
import com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class VacancyRateService {
    private VacancyRateRepository vacancyRateRepository;


    public List<VacancyrateDto> findBase(String product_Id,String type){
        List<VacancyrateDto> vacancyRate = vacancyRateRepository.findByLastmonth(product_Id,type);
        return vacancyRate;
    }
}
