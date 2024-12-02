package com.moaguide.domain.coupon;

import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto(c.couponId,ca.name,ca.createdAt) FROM CouponUser c,CouponAdmin ca where c.couponId=ca.id and c.nickname =:nickname and c.redeemed =:redeemed order by c.id")
    List<CouponUserDto> findByNickname(@Param("nickname") String nickname, @Param("redeemed") boolean redeemed);
}
