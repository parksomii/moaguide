package com.moaguide.refactor.building.service;


import com.moaguide.refactor.building.repository.LocationRepository;
import com.moaguide.refactor.building.dto.LocationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService {

	private LocationRepository locationRepository;

	public LocationDto locate(String id) {
		return locationRepository.findByProductId(id);
	}
}
