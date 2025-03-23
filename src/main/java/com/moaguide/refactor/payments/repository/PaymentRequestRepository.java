package com.moaguide.refactor.payments.repository;

import com.moaguide.dto.NewDto.customDto.billingDto.PaymentDto;
import com.moaguide.refactor.payments.entity.PaymentRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile({"blue","green"})
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,String> {

    @Modifying
    @Query("delete FROM PaymentRequest p where p.nickname =:nickname")
    void deletebyNickname(@Param("nickname") String nickname);

    @Query("select p.nickname FROM PaymentRequest p where p.NextPaymentDate =:nowDate")
    List<String> findByDate(@Param("nowDate") LocalDate nowDate);

    @Modifying
    @Query("delete FROM PaymentRequest p where p.nickname =:nickname and p.NextPaymentDate<:enddate")
    void deletebyNicknameAndDate(@Param("nickname")String nickname,@Param("enddate")LocalDate enddate);

    @Query("select new  com.moaguide.dto.NewDto.customDto.billingDto.PaymentDto(p.orderId,b.billingKey,b.customerKey,p.amount,p.nickname,p.failCount) FROM PaymentRequest p left join BillingInfo b on p.nickname = b.nickname where p.NextPaymentDate =:nowDate")
    List<PaymentDto> findByNextPaymentDate(@Param("nowDate") LocalDate nowDate);

    @Modifying
    @Query("update PaymentRequest p set p.failCount=p.failCount+1,p.NextPaymentDate=:date where p.nickname =:nickname and p.NextPaymentDate =:nowdate")
    void updatefailList(@Param("nickname")String nickname,@Param("date") LocalDate date,@Param("nowdate") LocalDate nowdate);

    @Query("select p.nickname fROM PaymentRequest p where p.failCount>=5 or p.NextPaymentDate<:endDate")
    List<String> findByFailCount(@Param("endDate") LocalDate date);

    @Modifying
    @Query("DELETE FROM PaymentRequest p WHERE p.nickname IN :nicknames")
    void deleteByFailCount(@Param("nicknames") List<String> nickname);
}
