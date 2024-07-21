package com.moaguide.service.building;

import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.vacancyrate.VacancyRate;
import com.moaguide.domain.building.vacancyrate.VacancyRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class VacancyRateService {
    private VacancyRateRepository vacancyRateRepository;


    public List<VacancyRate> findBase(String keyword,String type){
        List<VacancyRate> vacancyRate = vacancyRateRepository.findByLastmonth(keyword,type);
        return vacancyRate;
    }
}
