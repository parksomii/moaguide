package com.moaguide.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponAdminRepository extends JpaRepository<CouponAdmin, Long> {

    @Query("SELECT c FROM CouponAdmin c WHERE c.couponCode = :code AND c.nickname = :nickname")
    Optional<CouponAdmin> findByCodeAndNickname(@Param("code") String code, @Param("nickname") String nickname);

}
