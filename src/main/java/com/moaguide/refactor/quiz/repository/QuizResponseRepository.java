package com.moaguide.refactor.quiz.repository;

import com.moaguide.refactor.quiz.dto.QuizResponseDto;
import com.moaguide.refactor.quiz.entity.QuizResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {

	@Modifying
	@Transactional
	@Query("delete FROM QuizResponse q where q.nickname=:nickname")
	void deleteByNickname(@Param("nickname") String nickname);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuizResponseDto(q.quizId,q.type,q.failList,q.failAnswer) FROM QuizResponse q where q.nickname =:nickname")
	Optional<QuizResponseDto> findbyNickname(@Param("nickname") String nickname);
}
