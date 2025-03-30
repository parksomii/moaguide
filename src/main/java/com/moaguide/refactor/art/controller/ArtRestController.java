package com.moaguide.refactor.art.controller;

import com.moaguide.refactor.art.dto.ArtBaseResponseDto;
import com.moaguide.refactor.art.service.ArtService;
import com.moaguide.refactor.jwt.util.JwtUtil;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/art")
public class ArtRestController {

	private final ArtService artService;
	private final JwtUtil jwtUtil;

	@GetMapping("/base/{product_Id}")
	public ResponseEntity<Object> Base(@PathVariable String product_Id) {
		ArtBaseResponseDto artBaseResponse = artService.findArtBase(product_Id);
		// null 체크
		if (artBaseResponse == null) {
			return ResponseEntity.ok(new HashMap<>());
		}
		return ResponseEntity.ok(artBaseResponse);
	}
}
