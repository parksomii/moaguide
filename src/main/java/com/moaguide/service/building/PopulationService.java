package com.moaguide.service.building;

import com.moaguide.domain.building.population.Population;
import com.moaguide.domain.building.population.PopulationRepository;
import com.moaguide.dto.NewDto.PopulationDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@AllArgsConstructor
@Service
public class PopulationService {
    private final PopulationRepository populationRepository;

    public List<PopulationDto> findbydate(int id, Integer year, Integer month) {
        LocalDate firstDayOfTargetMonth = LocalDate.of(year, month, 1);
        List<PopulationDto> population = populationRepository.findByLastmonth(id, firstDayOfTargetMonth);
        return population;
    }
}
