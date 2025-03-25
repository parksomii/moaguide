package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.AreaRepository;
import com.moaguide.refactor.building.dto.AreaDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AreaService {

	private final AreaRepository areaRepository;

	@Transactional
	public List<AreaDto> findpolygon(String keyword) {
		List<AreaDto> area = areaRepository.findpolygon(keyword);
		return area;
	}
}
