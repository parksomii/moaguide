package com.moaguide.service.building;


import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.building.location.LocationRepository;
import com.moaguide.dto.LocationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocationService {
    private LocationRepository locationRepository;

    @Transactional(readOnly = false)
    public LocationDto locate(String id) {
        LocationDto location = locationRepository.findByProductId(id);
        return location;
    }
}
