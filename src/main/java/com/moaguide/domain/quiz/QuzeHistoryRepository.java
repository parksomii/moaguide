package com.moaguide.domain.quiz;

import com.moaguide.dto.NewDto.customDto.QuizRankDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuzeHistoryRepository extends JpaRepository<QuizHistory, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.QuizRankDto(q.nickname,q.score,q.time) from QuizHistory q order by q.quizId desc,q.score desc,q.time")
    List<QuizRankDto> findtop5(Pageable pageable);

    @Query("SELECT COUNT(q) > 0 FROM QuizHistory q WHERE q.nickname = :nickname AND q.quizId = :quizId")
    Boolean findByNickname(@Param("nickname") String nickname, @Param("quizId") long quizId);
}
