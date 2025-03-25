package com.moaguide.refactor.quiz.repository;

import com.moaguide.refactor.quiz.dto.QuestionCheckResponseDto;
import com.moaguide.refactor.quiz.dto.QuestionDto;
import com.moaguide.refactor.quiz.dto.QuestionLinkDto;
import com.moaguide.refactor.quiz.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'b' THEN 1 WHEN 'c' THEN 2 WHEN 'a' THEN 3 END")
	List<QuestionDto> findByTypeB(@Param("id") long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'c' THEN 1 WHEN 'a' THEN 2 WHEN 'b' THEN 3 END")
	List<QuestionDto> findByTypeC(@Param("id") long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'a' THEN 1 WHEN 'b' THEN 2 WHEN 'c' THEN 3 END")
	List<QuestionDto> findByTypeA(@Param("id") long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto(q.id,q.solution,q.score) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'b' THEN 1 WHEN 'c' THEN 2 WHEN 'a' THEN 3 END")
	List<QuestionCheckResponseDto> findByTypeBcheck(long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto(q.id,q.solution,q.score) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'c' THEN 1 WHEN 'a' THEN 2 WHEN 'b' THEN 3 END")
	List<QuestionCheckResponseDto> findByTypeCcheck(long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto(q.id,q.solution,q.score) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'a' THEN 1 WHEN 'b' THEN 2 WHEN 'c' THEN 3 END")
	List<QuestionCheckResponseDto> findByTypeAcheck(long id);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionLinkDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5,q.link) FROM Question q where q.quizId =:quizId and  q.id in :fail ORDER BY CASE q.type WHEN 'a' THEN 1 WHEN 'b' THEN 2 WHEN 'c' THEN 3 END ")
	List<QuestionLinkDto> findByFailIdTyAndTypeA(@Param("fail") List<Long> faillist,
		@Param("quizId") Long quizId);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionLinkDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5,q.link) FROM Question q where q.quizId =:quizId and  q.id in :fail ORDER BY CASE q.type WHEN 'b' THEN 1 WHEN 'c' THEN 2 WHEN 'a' THEN 3 END ")
	List<QuestionLinkDto> findByFailIdTyAndTypeB(@Param("fail") List<Long> faillist, Long quizId);

	@Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionLinkDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5,q.link) FROM Question q where q.quizId =:quizId and  q.id in :fail ORDER BY CASE q.type WHEN 'c' THEN 1 WHEN 'a' THEN 2 WHEN 'b' THEN 3 END ")
	List<QuestionLinkDto> findByFailIdTyAndTypeC(@Param("fail") List<Long> faillist, Long quizId);
}
