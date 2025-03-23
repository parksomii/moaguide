package com.moaguide.service.building;


import com.moaguide.refactor.building.repository.LocationRepository;
import com.moaguide.dto.LocationDto;
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
