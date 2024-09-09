package com.moaguide.service.building;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.businessarea.BusinessAreaRepository;
import com.moaguide.dto.NewDto.BusinessAreaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BusinessAreaService {
    private BusinessAreaRepository businessAreaRepository;


    @Transactional(readOnly = false)
    public BusinessAreaDto findBase(String id) {
        BusinessAreaDto businessArea = businessAreaRepository.findByproductId(id);
        return businessArea;
    }
}
