package com.moaguide.service.building;


import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.building.location.LocationRepository;
import com.moaguide.dto.LocationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService {
    private LocationRepository locationRepository;

    public Location locate(String id) {
        Location location = locationRepository.findByProductId(id);
        return location;
    }
}
