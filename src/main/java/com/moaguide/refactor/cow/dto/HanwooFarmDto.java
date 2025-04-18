package com.moaguide.refactor.cow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HanwooFarmDto {

	private String certificationNumber; // 인증번호
	private String certificationAgency; // 인증기관
	private String manager; // 관리책임자
	private String certifiedHeads; // 인증두수
	private String cattleBreed; // 축종
	private String initialDate; // 최초인증일

	public HanwooFarmDto(String certificationNumber, String certificationAgency, String manager,
		String certifiedHeads, String cattleBreed, String initialDate) {
		this.certificationNumber = certificationNumber;
		this.certificationAgency = certificationAgency;
		this.manager = manager;
		this.certifiedHeads = certifiedHeads;
		this.cattleBreed = cattleBreed;
		this.initialDate = initialDate;
	}
}
