package com.moaguide.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {

    @Query("delete FROM QuizResponse q where q.nickname=:nickname")
    void deleteByNickname(@Param("nickname") String nickname);
}
