package com.moaguide.domain.billing;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, String> {

    @Modifying
    @Transactional
    @Query("delete FROM BillingInfo b where b.nickname =:nickname")
    void deleteByNickname(@Param("nickname") String nickname);

    @Modifying
    @Query("update BillingInfo b set b.customerKey =:customerKey,b.billingKey =:billingKey,b.authenticatedAt=:date where b.nickname =:nickname")
    int update(@Param("customerKey") String customerKey,@Param("billingKey") String billingKey,@Param("nickname") String nickname,@Param("date") LocalDate date);

    @Query("SELECT b FROM BillingInfo b where b.nickname =:nickname")
    Optional<BillingInfo> findByNickname(@Param("nickname") String nickname);
}
