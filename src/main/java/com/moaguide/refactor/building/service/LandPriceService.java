package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.LandPriceRepository;
import com.moaguide.refactor.building.dto.LandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LandPriceService {

	private LandPriceRepository landPriceRepository;

	public List<LandDto> priceList(String id) {
		List<LandDto> list = landPriceRepository.findAllByproductId(id);
		return list;
	}
}
