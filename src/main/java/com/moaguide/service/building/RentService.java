package com.moaguide.service.building;

import com.moaguide.domain.building.rent.RentRepository;
import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class RentService {
    private RentRepository rentRepository;


    public List<RentDto> findBase(String product_Id, String type, int syear, int eyear) {
        List<RentDto> rent = rentRepository.findBytype(product_Id,type,syear,eyear);
        return rent;
    }

    @Transactional(readOnly = false)
    public List<TypeDto> findType(String productId) {
        List<TypeDto> rents = rentRepository.findType(productId);
        return rents;
    }
}
