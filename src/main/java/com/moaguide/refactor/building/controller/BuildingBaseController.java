package com.moaguide.refactor.building.controller;

import com.moaguide.refactor.building.dto.BuildingBaseDto;
import com.moaguide.refactor.building.dto.BuildingBaseResponseDto;
import com.moaguide.refactor.building.dto.LeaseDto;
import com.moaguide.refactor.building.dto.LocationDto;
import com.moaguide.refactor.building.service.LandRegistryService;
import com.moaguide.refactor.building.service.LeaseService;
import com.moaguide.refactor.building.service.LocationService;
import com.moaguide.refactor.util.EmptyCheckUtil;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/detail/building")
public class BuildingBaseController {

	private final LandRegistryService landRegistryService;
	private final LeaseService leaseService;
	private final LocationService locationService;

	/**
	 * 건물의 기본 정보를 조회하는 API
	 *
	 * @param product_Id 콘텐츠 고유 ID
	 * @return 건물의 기본 정보 응답 DTO
	 */
	@GetMapping("/base/{product_Id}")
	public ResponseEntity<Object> base(@PathVariable String product_Id) {
		BuildingBaseDto building = landRegistryService.findbase(product_Id);
		List<LeaseDto> leaseDtos = leaseService.detail(product_Id);

		// NULL, 빈 리스트 처리
		if (EmptyCheckUtil.isEmpty(building) || EmptyCheckUtil.isListEmpty(leaseDtos)) {
			return ResponseEntity.ok(new HashMap<>());
		}

		return ResponseEntity.ok(new BuildingBaseResponseDto(building, leaseDtos));
	}

	/**
	 * 위치 정보를 조회하는 API
	 *
	 * @param product_Id 콘텐츠 고유 ID
	 * @return 위치 정보 DTO 반환
	 */
	@GetMapping("/area/{product_Id}")
	public ResponseEntity<Object> area(@PathVariable String product_Id) {
		LocationDto location = locationService.locate(product_Id);
		return ResponseEntity.ok(location);
	}
}
