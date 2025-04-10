package com.moaguide.refactor.contents.dto;

import java.math.BigInteger;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentPublishDto {

	private String name;    // 상품명
	private String genre;   // 장르
	private String type;    // 증권종류
	private BigInteger minAmount;   // 최소모집목표금액
	private BigInteger maxAmount;   // 최대모집목표금액
	private Integer piece;          // 최소 모집수량
	private Integer basePrice;      // 1주당 가격
	private Integer minInvestment;  // 최소 투자금액
	private Date issuanceDate; // 증권발행일
	private Date expirationDate;   // 증권만기일

}
