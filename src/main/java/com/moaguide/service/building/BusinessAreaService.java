package com.moaguide.service.building;

import com.moaguide.domain.building.businessarea.BusinessAreaRepository;
import com.moaguide.dto.NewDto.BusinessAreaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusinessAreaService {
    private BusinessAreaRepository businessAreaRepository;


    public BusinessAreaDto findBase(String product_Id) {
        BusinessAreaDto businessArea = businessAreaRepository.findByproductId(product_Id);
        return businessArea;
    }
}
