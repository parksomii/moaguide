package com.moaguide.refactor.cow.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HanwooPublishDto {

	// 발행정보
	private String name;            // 작품명
	private String type;       // 증권종류
	private int piece;              // 증권수
	private int basePrice;          // 1주당 발행액
	private String totalPrice;      // 총 모집액
	private String recruitingType;  // 모집방법
	private String rightsStructure; // 권리구조
	private String revenueStructure; // 수익구조
	private LocalDate subscriptionDate;    // 청약공고일
	private LocalDate paymentDate;     // 납입기일
	private LocalDate allocationDate;  // 배정공고일
	private LocalDate criteriaDate;    // 배정기준일

	// 매개변수 있는 생성자
	public HanwooPublishDto(String name, String type, int piece, int basePrice, String totalPrice,
		String recruitingType,
		String rightsStructure, String revenueStructure, LocalDate paymentDate,
		LocalDate subscriptionDate,
		LocalDate allocationDate, LocalDate criteriaDate) {
		this.name = name;
		this.type = type;
		this.piece = piece;
		this.basePrice = basePrice;
		this.totalPrice = totalPrice;
		this.recruitingType = recruitingType;
		this.rightsStructure = rightsStructure;
		this.revenueStructure = revenueStructure;
		this.paymentDate = paymentDate;
		this.subscriptionDate = subscriptionDate;
		this.allocationDate = allocationDate;
		this.criteriaDate = criteriaDate;
	}
}
