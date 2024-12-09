package com.moaguide.domain.card;

import com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c FROM Card c where c.nickname = :nickname")
    Optional<Card> findBynickname(@Param("nickname") String nickname);

    @Modifying
    @Transactional
    @Query("update Card c set c.cardname = null,c.cardNumber=null  where c.nickname =:nickname")
    void deleteByNickname(@Param("nickname") String nickname);

    @Modifying
    @Query("update Card c set c.cardNumber = :cardNumber,c.cardname = :cardName where c.nickname =:nickname")
    int update(@Param("nickname") String nickname,@Param("cardName") String cardCompany,@Param("cardNumber") Integer cardNumber);


    @Modifying
    @Query("update Card c set c.subscriptionStartDate = :nowDate ,c.subscriptionEndDate=:enddate  where c.nickname =:nickname")
    void updateSubscript(@Param("nickname")String nickname, @Param("nowDate") Date nowDate, @Param("enddate") Date date);

    @Query("select new com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto(c.subscriptionStartDate,c.subscriptionEndDate,p.NextPaymentDate) FROM Card c left join PaymentRequest p on c.nickname=p.nickname and c.subscriptionEndDate=p.NextPaymentDate where c.nickname =:nickname ")
    SubscriptDateDto findDate(@Param("nickname") String nickname);

    @Modifying
    @Query("update Card c set c.subscriptionStartDate = null,c.subscriptionEndDate=null  where c.nickname =:nickname")
    void deleteByNicknameDate(@Param("nickname") String nickname);

    @Modifying
    @Query("update Card c set c.subscriptionEndDate=:enddate  where c.nickname =:nickname")
    void updateSubscriptByCron(@Param("nickname") String nickname,@Param("enddate")Date enddate);

    @Query("select c.nickname FROM Card c where c.subscriptionEndDate =:date")
    List<String> findByDate(Date date);

    @Query("update Card c set c.subscriptionEndDate=null  where c.nickname in :nickname")
    void updateSubscriptBylist(@Param("nickname")List<String> updateNickname);
}
