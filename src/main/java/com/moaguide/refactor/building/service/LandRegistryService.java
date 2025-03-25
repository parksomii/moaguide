package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.LandRegistryRepository;
import com.moaguide.refactor.building.dto.BuildingBaseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LandRegistryService {

	private final LandRegistryRepository landRegistryRepository;


	@Transactional(readOnly = false)
	public BuildingBaseDto findbase(String productId) {
		BuildingBaseDto building = landRegistryRepository.findDetail(productId);
		return building;
	}
}
