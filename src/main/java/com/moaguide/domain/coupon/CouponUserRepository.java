package com.moaguide.domain.coupon;

import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto(c.couponId,ca.name,ca.createdAt) FROM CouponUser c,CouponAdmin ca where c.couponId=ca.id and c.nickname =:nickname and c.redeemed =:redeemed order by c.id")
    List<CouponUserDto> findByNickname(@Param("nickname") String nickname, @Param("redeemed") boolean redeemed);

    @Query("SELECT ca.months FROM CouponUser c, CouponAdmin ca where c.couponId = ca.id and c.nickname=:nickname and c.couponId=:couponId")
    Optional<Integer> findByNicknameAndCouponId(@Param("nickname") String nickname, @Param("couponId") Long couponId);

    @Modifying
    @Query("update CouponUser c set c.redeemed=:bol,c.redeemedAt =:date where c.nickname=:nickname")
    void updateRedeemed(@Param("bol") boolean bol, @Param("date") LocalDate date, @Param("nickname")String name);
}
