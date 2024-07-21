package com.moaguide.service.building;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.businessarea.BusinessAreaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusinessAreaService {
    private BusinessAreaRepository businessAreaRepository;

    public BusinessArea findBase(String id) {
        BusinessArea businessArea = businessAreaRepository.findByproductId(id);
        return businessArea;
    }
}
