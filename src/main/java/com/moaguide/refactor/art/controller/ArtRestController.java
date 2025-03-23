package com.moaguide.refactor.art.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
import com.moaguide.refactor.art.service.ArtService;
import com.moaguide.refactor.security.jwt.JWTUtil;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/art")
public class ArtRestController {

	private final ArtService artService;
	private final JWTUtil jwtUtil;


	@GetMapping("/{product_Id}")
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
		ArtDetailDto artDetail = artService.findArtDetail(product_Id, Nickname);
		return ResponseEntity.ok().body(artDetail);

	}

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
