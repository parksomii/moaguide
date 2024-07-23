package com.moaguide.service.building;

import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.rent.RentRepository;
import com.moaguide.dto.NewDto.TypeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RentService {
    private RentRepository rentRepository;


    public List<Rent> findBase(String keyword,String type) {
        List<Rent> rent = rentRepository.findBytype(keyword,type);
        return rent;
    }

    public List<TypeDto> findType(String keyword) {
        List<TypeDto> rents = rentRepository.findType(keyword);
        return rents;
    }
}
