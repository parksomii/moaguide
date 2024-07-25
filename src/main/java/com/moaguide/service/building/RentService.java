package com.moaguide.service.building;

import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.rent.RentRepository;
import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RentService {
    private RentRepository rentRepository;


    public List<RentDto> findBase(String keyword, String type) {
        List<RentDto> rent = rentRepository.findBytype(keyword,type);
        return rent;
    }

    public List<TypeDto> findType(String keyword) {
        List<TypeDto> rents = rentRepository.findType(keyword);
        return rents;
    }
}
