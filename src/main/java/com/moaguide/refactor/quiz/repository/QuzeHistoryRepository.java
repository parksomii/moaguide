package com.moaguide.refactor.quiz.repository;

import com.moaguide.refactor.quiz.dto.QuizHistoryDto;
import com.moaguide.refactor.quiz.dto.QuizRankDto;
import com.moaguide.refactor.quiz.entity.QuizHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuzeHistoryRepository extends JpaRepository<QuizHistory, Long> {

	@Query("SELECT new com.moaguide.refactor.quiz.dto.QuizRankDto(q.nickname,q.score,q.time) from QuizHistory q order by q.score desc,q.time")
	List<QuizRankDto> findtop5(Pageable pageable);

	@Query("SELECT COUNT(q) > 0 FROM QuizHistory q WHERE q.nickname = :nickname AND q.quizId = :quizId")
	Boolean findByNickname(@Param("nickname") String nickname, @Param("quizId") long quizId);

	@Query("SELECT COUNT(q) > 0 FROM QuizHistory q WHERE q.nickname = :nickname")
	Boolean findByNickname(@Param("nickname") String nickname);

	@Query("SELECT q FROM QuizHistory q where q.nickname =:nickname")
	Optional<QuizHistory> findBynick(String nickname);

	@Query("select avg(q.score) FROM QuizHistory q")
	double findAvarage();

	@Query("select new com.moaguide.refactor.quiz.dto.QuizRankDto(q.nickname,q.score,q.time) from QuizHistory q order by q.score desc,q.time")
	List<QuizRankDto> findList(Pageable pageable);

	@Modifying
	@Transactional
	@Query("delete FROM QuizHistory q where q.nickname = :nickname")
	void deleteByNickname(@Param("nickname") String nickname);

	@Query("SELECT new com.moaguide.refactor.quiz.dto.QuizHistoryDto(q.score,q.naver,q.insta,q.time) FROM QuizHistory q where q.nickname=:nickname")
	Optional<QuizHistoryDto> findByresponse(@Param("nickname") String nickname);
}
