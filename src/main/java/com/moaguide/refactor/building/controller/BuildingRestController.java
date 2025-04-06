package com.moaguide.refactor.building.controller;


import com.moaguide.refactor.building.dto.BuildingSubwayResponseDto;
import com.moaguide.refactor.building.dto.LandDto;
import com.moaguide.refactor.building.dto.PopulationDto;
import com.moaguide.refactor.building.dto.RentDto;
import com.moaguide.refactor.building.dto.StayDayDto;
import com.moaguide.refactor.building.dto.StayRateDto;
import com.moaguide.refactor.building.dto.VacancyrateDto;
import com.moaguide.refactor.building.service.graph.LandPriceService;
import com.moaguide.refactor.building.service.graph.PopulationService;
import com.moaguide.refactor.building.service.graph.RentService;
import com.moaguide.refactor.building.service.graph.StayService;
import com.moaguide.refactor.building.service.graph.SubwayService;
import com.moaguide.refactor.building.service.graph.VacancyRateService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/building")
public class BuildingRestController {

	private final RentService rentService;
	private final LandPriceService landPriceService;
	private final StayService stayService;
	private final SubwayService subwayService;
	private final PopulationService populationService;
	private final VacancyRateService vacancyRateService;


	@GetMapping("/land/{product_Id}")
	public ResponseEntity<Object> land(@PathVariable String product_Id) {
		List<LandDto> landPrice = landPriceService.priceList(product_Id);
		Map<String, Object> response = new HashMap<>();
		// null 처리
		if (landPrice == null) {
			response.put("lands", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
		response.put("lands", landPrice);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/subway/{productId}")
	public ResponseEntity<Object> subway(@PathVariable String productId) {
		BuildingSubwayResponseDto subwayResponseDto = subwayService.findByProductId(productId);
		// null 처리
		if (subwayResponseDto == null || subwayResponseDto.getSubwayDay().isEmpty()
			|| subwayResponseDto.getSubwayMonth().isEmpty()) {
			return ResponseEntity.ok(new BuildingSubwayResponseDto());
		}
		return ResponseEntity.ok(subwayResponseDto);
	}

	@GetMapping("/population/{product_Id}")
	public ResponseEntity<Object> population(@PathVariable String product_Id) {
		List<PopulationDto> populationDto = populationService.findbydate(product_Id);
		Map<String, Object> response = new HashMap<>();
		// null 처리
		if (populationDto == null) {
			response.put("populations", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
		response.put("populations", populationDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/rentrate/{product_Id}")
	public ResponseEntity<Object> rentrate(@PathVariable String product_Id,
		@RequestParam String type, @RequestParam int syear, @RequestParam int eyear) {
		Map<String, List<RentDto>> rentDtos = rentService.getRentByRegion(product_Id, type, syear,
			eyear);
		Map<String, Object> response = new HashMap<>();

		// null 처리
		if (rentDtos == null) {
			response.put("populations", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
		response.put("rent", rentDtos);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/vacancyrate/{product_Id}")
	public ResponseEntity<Object> vacancyrate(@PathVariable String product_Id,
		@RequestParam String type, @RequestParam int syear, @RequestParam int eyear) {
		Map<String, List<VacancyrateDto>> vacancyrateDtos = vacancyRateService.findBase(product_Id,
			type, syear, eyear);
		// null 처리
		Map<String, Object> response = new HashMap<>();

		if (vacancyrateDtos == null) {
			response.put("populations", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
		response.put("vacancyrate", vacancyrateDtos);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/stay/day/{productId}")
	public ResponseEntity<Object> stayday(@PathVariable String productId, @RequestParam int syear,
		@RequestParam int eyear) {
		String keyword;
		Map<String, Object> response = new HashMap<>();

		if (productId.equals("sou.6")) {
			keyword = "전주 시화연풍";
			List<StayDayDto> stayday = stayService.findbykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("kasa.KR011A20000052")) {
			keyword = "부티크호텔 더 페이즈";
			List<StayDayDto> stayday = stayService.findbykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("kasa.KR011A20000090")) {
			keyword = "북촌 월하재";
			List<StayDayDto> stayday = stayService.findbykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("funble.FB2412111")) {
			keyword = "더 코노셔 여의도";
			List<StayDayDto> stayday = stayService.findbykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else {
			response.put("object", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/stay/rate/{productId}")
	public ResponseEntity<Object> stayrate(@PathVariable String productId, @RequestParam int syear,
		@RequestParam int eyear) {
		String keyword;
		Map<String, Object> response = new HashMap<>();
		if (productId.equals("sou.6")) {
			keyword = "전주 시화연풍";
			List<StayRateDto> stayday = stayService.findRateBykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("kasa.KR011A20000052")) {
			keyword = "부티크호텔 더 페이즈";
			List<StayRateDto> stayday = stayService.findRateBykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("kasa.KR011A20000090")) {
			keyword = "북촌 월하재";
			List<StayRateDto> stayday = stayService.findRateBykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else if (productId.equals("funble.FB2412111")) {
			keyword = "더 코노셔 여의도";
			List<StayRateDto> stayday = stayService.findRateBykeyword(keyword, syear, eyear);
			response.put("object", stayday);
			return ResponseEntity.ok(response);
		} else {
			response.put("object", new ArrayList<>());
			return ResponseEntity.ok(response);
		}
	}

}