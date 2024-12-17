package com.moaguide.domain.coupon;

import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import com.moaguide.dto.NewDto.customDto.billingDto.BillingCouponUSer;
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

    @Query("SELECT ca.months FROM CouponUser c, CouponAdmin ca where c.couponId = ca.id and c.nickname=:nickname and c.couponId=:couponId and c.redeemed is false")
    Optional<Integer> findByNicknameAndCouponId(@Param("nickname") String nickname, @Param("couponId") Long couponId);

    @Modifying
    @Query("update CouponUser c set c.redeemed=:bol,c.redeemedAt =:date where c.nickname=:nickname")
    void updateRedeemed(@Param("bol") boolean bol, @Param("date") LocalDate date, @Param("nickname")String name);

    @Modifying
    @Query("update CouponUser c set c.redeemed=:bol,c.redeemedAt =:date where c.nickname=:nickname and c.couponId = :couponId")
    void updateRedeemedWithCouponId(@Param("bol") boolean b,@Param("date") LocalDate now, @Param("nickname") String nickname, @Param("couponId") Long couponId);

    @Query("select new com.moaguide.dto.NewDto.customDto.billingDto.BillingCouponUSer( c.id, c.couponId, ca.name,c.nickname,ca.months) " +
            "FROM CouponUser c " +
            "join CouponAdmin ca on c.couponId = ca.id " +
            "where c.id in (select ci.id FROM CouponUser ci where ci.nickname in :nickname group by ci.nickname)")
    List<BillingCouponUSer> findAllByNickname(@Param("nickname") List<String> nicknameList);
}
