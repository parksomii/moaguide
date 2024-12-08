package com.moaguide.domain.billding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

    @Query("select p FROM PaymentLog  p where p.nickname=:nickname")
    List<PaymentLog> findAll(@Param("nickname") String nickname);

    @Query("delete FROM PaymentLog p where p.nickname=:nickname")
    void deleteByNickname(@Param("nickname") String nickname);
}
