package com.moaguide.domain.coupon;

import com.moaguide.dto.NewDto.customDto.Coupon.CouponAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponAdminRepository extends JpaRepository<CouponAdmin, Long> {

    @Query("SELECT c FROM CouponAdmin c WHERE c.couponCode = :code AND c.nickname = :nickname")
    Optional<CouponAdmin> findByCodeAndNickname(@Param("code") String code, @Param("nickname") String nickname);

    @Query("select new com.moaguide.dto.NewDto.customDto.Coupon.CouponAdminDto(c.name,c.couponCode,c.createdAt,c.months,c.nickname,cu.couponId,cu.redeemed,cu.redeemedAt) FROM CouponAdmin c LEFT JOIN CouponUser cu ON c.id = cu.couponId ")
    Page<CouponAdminDto> findByAll(Pageable pages);
}
