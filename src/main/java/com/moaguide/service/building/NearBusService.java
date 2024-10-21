package com.moaguide.service.building;

import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.building.near.NearBusRepository;
import com.moaguide.dto.NewDto.BuildingDto.NearBusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NearBusService {
    private final NearBusRepository nearBusRepository;


}
