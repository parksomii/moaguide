package com.moaguide.refactor.coupon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "Coupon_Admin",
	uniqueConstraints = @UniqueConstraint(columnNames = {"coupon_Code",
		"nickname"}) // UNIQUE 제약 조건 설정
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponAdmin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Primary Key

	private String name; // 쿠폰 이름

	@Column(name = "coupon_code")
	private String couponCode; // 쿠폰 고유 번호

	@Column(name = "created_at")
	private LocalDate createdAt; // 발행일

	private Integer months; // 쿠폰 적용 기간 (개월수)

	private String nickname; // User와 외래 키로 연결
}