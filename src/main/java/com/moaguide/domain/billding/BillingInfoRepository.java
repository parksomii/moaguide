package com.moaguide.domain.billding;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, String> {

    @Modifying
    @Transactional
    @Query("delete FROM BillingInfo b where b.nickname =:nickname")
    void deleteByNickname(@Param("nickname") String nickname);
}
