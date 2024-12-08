package com.moaguide.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}