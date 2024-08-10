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

    Optional<User> findByEmailAndLoginType(String email, String loginType);

}
