package com.moaguide.refactor.building.service;

import com.moaguide.refactor.building.repository.LeaseRepository;
import com.moaguide.refactor.building.dto.LeaseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaseService {

	private final LeaseRepository leaseRepository;

	public List<LeaseDto> detail(String id) {
		List<LeaseDto> lease = leaseRepository.findByproductId(id);
		return lease;
	}
}
