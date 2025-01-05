package com.moaguide.domain.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "Coupon_User",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nickname", "couponId"}) // 닉네임과 쿠폰 ID 조합에 대한 UNIQUE 제약 조건
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key

    private String nickname; // 사용자 닉네임 (외래 키로 사용)

    @Column(name = "coupon_Id", nullable = false)
    private Long couponId; // 쿠폰 ID (외래 키로 사용)

    private Boolean redeemed = false; // 쿠폰 사용 여부, 기본값 FALSE

    @Column(name = "redeemed_At")
    private LocalDate redeemedAt; // 쿠폰 사용 날짜 (NULL 가능)

    public CouponUser(String nickname, Long couponId, Boolean redeemed) {
        this.nickname = nickname;
        this.couponId = couponId;
        this.redeemed = redeemed;
    }
}
