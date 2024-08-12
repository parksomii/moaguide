package com.moaguide.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u from User u where u.nickname=:nickname")
    Optional<User> findByNickname(@Param("nickname") String nickName);

    @Query("SELECT u from User u where u.nickname=:nickname")
    User findUserByNickName(@Param("nickname") String nickname);

    @Query("SELECT u from User u where u.email=:email and u.loginType=:logintype")
    Optional<User> findByEmailAndLoginType(@Param("email") String email,@Param("logintype") String loginType);

}
