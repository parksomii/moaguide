package com.moaguide.service.building;

import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.landregistry.LandRegistryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LandRegistryService {
    private final LandRegistryRepository landRegistryRepository;

    public LandRegistry fingById(String id) {
        LandRegistry landRegistry = landRegistryRepository.findByProductId(id);
        return landRegistry;
    }
}
