package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.near.NearSubwayRepository;
import com.moaguide.refactor.building.dto.NearSubwayDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NearSubwayService {

	private final NearSubwayRepository nearSubwayRepository;

	@Transactional
	public List<NearSubwayDto> findBykeyword(String productId) {
		List<NearSubwayDto> nearSubway = nearSubwayRepository.findBykeyword(productId);
		return nearSubway;
	}
}
