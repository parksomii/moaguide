package com.moaguide.service.building;

import com.moaguide.domain.building.population.PopulationRepository;
import com.moaguide.dto.NewDto.BuildingDto.PopulationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class PopulationService {
    private final PopulationRepository populationRepository;

    public List<PopulationDto> findbydate(String ProductId, Integer year, Integer month) {
        LocalDate firstDayOfTargetMonth = LocalDate.of(year, month, 1);
        List<PopulationDto> population = populationRepository.findByLastmonth(ProductId, firstDayOfTargetMonth);
        return population;
    }
}
