package com.moaguide.domain.billing;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

    @Query("select p FROM PaymentLog  p where p.nickname=:nickname")
    List<PaymentLog> findAll(@Param("nickname") String nickname);

    @Modifying
    @Query("delete FROM PaymentLog p where p.nickname=:nickname")
    void deleteByNickname(@Param("nickname") String nickname);

    @Query("select p.orderId FROM PaymentLog p where p.nickname=:nickname order by  p.id desc")
    String findLog(@Param("nickname") String nickname, Pageable page);
}
