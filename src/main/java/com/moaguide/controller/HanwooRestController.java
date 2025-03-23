package com.moaguide.controller;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooDetailDto;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.service.hanwoo.HanwooPriceService;
import com.moaguide.service.hanwoo.HanwooService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/hanwoo/")
public class HanwooRestController {

	private final HanwooService hanwooService;
	private final HanwooPriceService hanwooPriceService;
	private final JWTUtil jwtUtil;

	@GetMapping("{product_Id}")
	public ResponseEntity<Object> detail(@PathVariable String product_Id,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String Nickname;
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		HanwooDetailDto hanwooDetail = hanwooService.findHanwooDetail(product_Id, Nickname);
		return ResponseEntity.ok().body(hanwooDetail);
	}

	@GetMapping("base/{product_Id}")
	public ResponseEntity<Object> Base(@PathVariable String product_Id) {
		HanwooBaseResponseDto hanwoo = hanwooService.findDetail(product_Id);
		return ResponseEntity.ok(hanwoo);
	}

	@GetMapping("sub/hanwooPrice")
	public ResponseEntity<Object> getHanwooPriceData(@RequestParam String category,
		@RequestParam int month) {
		List<?> result = hanwooPriceService.getHanwooPriceData(category, month);
//        Map<String, Object> data = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		// null 체크
		if (result == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		// name 필드 추가
		if ("grade1Rate".equals(category)) {
			String name = "1등급이상 출현율";
			response.put("name", name);
		} else if ("productionCost".equals(category)) {
			String name = "두당 생산비";
			response.put("name", name);
		} else if ("averagePrice".equals(category)) {
			String name = "두당 평균 도매가격";
			response.put("name", name);
		} else if ("cattlePrice".equals(category)) {
			String name = "거세우 평균가격";
			response.put("name", name);
		} else {
			return null;
		}
		// object 필드에 hanwooMarket 리스트 추가
		response.put("object", result);

		// 응답 반환
		return ResponseEntity.ok(response);
	}

	@GetMapping("sub/hanwoomarket")
	public ResponseEntity<?> getHanwooMarket(@RequestParam String category,
		@RequestParam int month) {
		List<?> hanwooMarket = hanwooPriceService.findHanwooMarket(category, month);
		Map<String, Object> response = new HashMap<>();
		// null 체크
		if (hanwooMarket == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		if (hanwooMarket.isEmpty()) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		if ("cattlePopulation".equals(category)) {
			String name = "한우 사육두수";
			response.put("name", name);
		} else if ("cattleSale".equals(category)) {
			String name = "연간 매각두수";
			response.put("name", name);
		} else if ("cattleFarm".equals(category)) {
			String name = "한우 사육농가수";
			response.put("name", name);
		} else if ("cattleTransaction".equals(category)) {
			String name = "한우 거래정육량";
			response.put("name", name);
		} else {
			return null;
		}
		// object 필드에 hanwooMarket 리스트 추가
		response.put("object", hanwooMarket);

		// 응답 반환
		return ResponseEntity.ok(response);
	}
}
