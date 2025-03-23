package com.moaguide.domain.card;

import com.moaguide.dto.NewDto.customDto.billingDto.LocalSubscriptDateDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("local")
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.billingDto.LocalSubscriptDateDto(c.subscriptionStartDate,c.subscriptionEndDate,p.NextPaymentDate) FROM Card c left join LocalPaymentRequest p on c.nickname=p.nickname and c.subscriptionEndDate=p.NextPaymentDate where c.nickname =:nickname ")
    Optional<LocalSubscriptDateDto> findDate(@Param("nickname") String nickname);


    @Modifying
    @Query("update Card c set c.subscriptionStartDate = :nowDate ,c.subscriptionEndDate=:enddate  where c.nickname =:nickname")
    void updateSubscript(@Param("nickname")String nickname, @Param("nowDate") LocalDateTime nowDate, @Param("enddate") LocalDateTime date);

    @Modifying
    @Query("update Card c set c.subscriptionEndDate=:enddate  where c.nickname =:nickname")
    void updateSubscriptByCron(@Param("nickname") String nickname,@Param("enddate")LocalDateTime enddate);

    @Query("select c.nickname FROM Card c where c.subscriptionEndDate <=:date")
    List<String> findByDate(LocalDateTime date);

    @Modifying
    @Query("update Card c set c.subscriptionEndDate=null  where c.nickname in :nickname")
    void updateSubscriptBylist(@Param("nickname")List<String> updateNickname);

    @Modifying
    @Query("update Card c set c.subscriptionEndDate=null,c.subscriptionStartDate=null where c.nickname =:nickname")
    void deleteByNicknameDate(@Param("nickname") String nickname);
}
