package com.moaguide.domain.quiz;

import com.moaguide.dto.NewDto.customDto.QuizRankDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuzeHistoryRepository extends JpaRepository<QuizHistory, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.QuizRankDto(q.nickname,q.score,q.time) from QuizHistory q order by q.score desc,q.time")
    List<QuizRankDto> findtop5(Pageable pageable);

    @Query("SELECT COUNT(q) > 0 FROM QuizHistory q WHERE q.nickname = :nickname AND q.quizId = :quizId")
    Boolean findByNickname(@Param("nickname") String nickname, @Param("quizId") long quizId);

    @Query("SELECT q FROM QuizHistory q where q.nickname =:nickname")
    Optional<QuizHistory> findBynick(String nickname);

    @Query("select avg(q.score) FROM QuizHistory q")
    double findAvarage();

    @Query("select new com.moaguide.dto.NewDto.customDto.QuizRankDto(q.nickname,q.score,q.time) from QuizHistory q order by q.score desc,q.time")
    List<QuizRankDto> findList(Pageable pageable);

    @Query("delete FROM QuizHistory q where q.nickname = :nickname")
    void deleteByNickname(@Param("nickname") String nickname);
}
