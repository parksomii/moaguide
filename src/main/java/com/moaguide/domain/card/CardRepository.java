package com.moaguide.domain.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c FROM Card c where c.nickname = :nickname")
    Optional<Card> findBynickname(@Param("nickname") String nickname);

    @Modifying
    @Transactional
    @Query("delete FROM Card c where c.nickname =:nickname")
    void deleteByNickname(@Param("nickname") String nickname);

}
