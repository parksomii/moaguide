package com.moaguide.service.building;

import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.rent.RentRepository;
import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.dto.TypeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private TypeDto convertToTypeDto(String rentType) {
        TypeDto typeDto = new TypeDto();
        typeDto.setType(rentType);
        return typeDto;
    }

    public List<TypeDto> findType(String keyword) {
        List<Optional<String>> rents = rentRepository.findType(keyword);
        List<TypeDto> typeDtoList = new ArrayList<>();

        for (Optional<String> rentOptional : rents) {
            rentOptional.ifPresent(rentType -> typeDtoList.add(convertToTypeDto(rentType)));
        }

        return typeDtoList;
    }
}
