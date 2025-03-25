package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.near.NearBusRepository;
import com.moaguide.refactor.building.dto.NearBusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NearBusService {

	private final NearBusRepository nearBusRepository;


	public List<NearBusDto> findNearBus(String productId) {
		return nearBusRepository.findBykeyword(productId);
	}
}
