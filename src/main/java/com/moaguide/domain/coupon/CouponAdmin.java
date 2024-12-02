package com.moaguide.domain.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "Coupon_Admin",
        uniqueConstraints = @UniqueConstraint(columnNames = {"couponCode", "nickname"}) // UNIQUE 제약 조건 설정
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

    private LocalDate createdAt; // 발행일

    private Integer months; // 쿠폰 적용 기간 (개월수)

    private String nickname; // User와 외래 키로 연결
}