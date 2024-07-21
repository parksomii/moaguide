package com.moaguide.service.building;

import com.moaguide.domain.building.population.Population;
import com.moaguide.domain.building.population.PopulationRepository;
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




    public List<Population>  findBase(int districtsId ,LocalDate date) {
        LocalDate firstDayOfTargetMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        List<Population> population = populationRepository.findByLastmonth(districtsId, firstDayOfTargetMonth);
        return population;
    }

    public List<Population> findbydate(int id, Integer year, Integer month) {
        LocalDate firstDayOfTargetMonth = LocalDate.of(year, month, 1);
        List<Population> population = populationRepository.findByLastmonth(id, firstDayOfTargetMonth);
        return population;
    }
}
