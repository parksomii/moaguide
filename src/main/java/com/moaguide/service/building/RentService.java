package com.moaguide.service.building;

import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.rent.RentRepository;
import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class RentService {
    private RentRepository rentRepository;


    public List<RentDto> findBase(String keyword, String type) {
        List<RentDto> rent = rentRepository.findBytype(keyword,type);
        return rent;
    }

    public List<TypeDto> findType(String keyword) {
        List<TypeDto> rents = rentRepository.findType(keyword);
        log.info("렌트 실험: {}", rents);
        return rents;
    }
}
