package com.moaguide.refactor.cow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HanwooBaseResponseDto {

	// 발행정보
	private HanwooPublishDto publish;
	// 농가정보
	private HanwooFarmDto farm;

	public HanwooBaseResponseDto(HanwooPublishDto publishDto, HanwooFarmDto farm) {
		this.publish = publishDto;
		this.farm = farm;
	}
}
