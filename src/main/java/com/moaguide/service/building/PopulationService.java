package com.moaguide.service.building;

import com.moaguide.domain.building.population.PopulationRepository;
import com.moaguide.dto.NewDto.BuildingDto.PopulationDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PopulationService {
    private final PopulationRepository populationRepository;

    public List<PopulationDto> findbydate(String ProductId) {
        Pageable page = Pageable.ofSize(7);
        List<PopulationDto> population = populationRepository.findByLastmonth(ProductId,page);
        return population;
    }
}
