package com.moaguide.domain.user;

import com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto;
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
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u from User u where u.nickname=:nickname")
    Optional<User> findByNickname(@Param("nickname") String nickName);

    @Query("SELECT u from User u where u.nickname=:nickname")
    User findUserByNickName(@Param("nickname") String nickname);

    @Query("SELECT u from User u where u.email=:email and u.loginType=:logintype")
    Optional<User> findByEmailAndLoginType(@Param("email") String email,@Param("logintype") String loginType);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.nickname = :change WHERE u.nickname = :nickname")
    void updateNickname(@Param("nickname") String findNickname, @Param("change") String changeNickname);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password  = :changePassword WHERE u.email = :email AND u.loginType ='Local' ")
    void updatePasswordbyEmail(@Param("email") String nickname, @Param("changePassword") String changePassword);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password  = :changePassword WHERE u.nickname = :nickname AND u.loginType ='Local' ")
    void updatePasswordbyNickname(@Param("nickname") String nickname, @Param("changePassword") String changePassword);

    @Transactional
    @Modifying
    @Query("update User u Set u.marketingConsent = :status where u.nickname =:nickname ")
    void updateMarketting(@Param("nickname")String nickname,@Param("status") int status);


    @Transactional
    @Modifying
    @Query("update User u Set u.role =:role where u.nickname =:nickname ")
    void updateRole(@Param("nickname") String nickname,@Param("role")Role role);

    @Modifying
    @Query("update User u Set u.role =:role where u.nickname in :nickname ")
    void updateRoleByDate(@Param("role") Role role, @Param("nickname")List<String> updateNickname);

    @Modifying
    @Query("update User u set u.subscriptionStartDate = null,u.subscriptionEndDate=null  where u.nickname =:nickname")
    void deleteByNickname(@Param("nickname")String nickname);

    @Modifying
    @Query("update User u set u.cardNumber = :cardNumber,u.cardname = :cardName where u.nickname =:nickname")
    int updateByCard(@Param("nickname") String nickname,@Param("cardName") String cardCompany,@Param("cardNumber") Integer cardNumber);


    @Modifying
    @Query("update User u set u.subscriptionStartDate = :nowDate ,u.subscriptionEndDate=:enddate  where u.nickname =:nickname")
    void updateSubscript(@Param("nickname")String nickname, @Param("nowDate") LocalDate nowDate, @Param("enddate") LocalDate date);

    @Query("select new com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto(u.subscriptionStartDate,u.subscriptionEndDate,p.NextPaymentDate) FROM User u left join PaymentRequest p on u.nickname=p.nickname and u.subscriptionEndDate=p.NextPaymentDate where u.nickname =:nickname ")
    SubscriptDateDto findDate(@Param("nickname") String nickname);

    @Modifying
    @Query("update User u set u.subscriptionStartDate = null,u.subscriptionEndDate=null  where u.nickname =:nickname")
    void deleteByNicknameDate(@Param("nickname") String nickname);

    @Modifying
    @Query("update User u set u.subscriptionEndDate=:enddate  where u.nickname =:nickname")
    void updateSubscriptByCron(@Param("nickname") String nickname,@Param("enddate")LocalDate enddate);

    @Query("select u.nickname FROM User u where u.subscriptionEndDate =:date")
    List<String> findByDate(LocalDate date);

    @Query("update User u set u.subscriptionEndDate=null  where u.nickname in :nickname")
    void updateSubscriptBylist(@Param("nickname")List<String> updateNickname);
}