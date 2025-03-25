package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.PopulationRepository;
import com.moaguide.refactor.building.dto.PopulationDto;
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
		List<PopulationDto> population = populationRepository.findByLastmonth(ProductId, page);
		return population;
	}
}
