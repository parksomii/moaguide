package com.moaguide.domain.quiz;

import com.moaguide.dto.NewDto.customDto.QuestionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'b' THEN 1 WHEN 'c' THEN 2 WHEN 'a' THEN 3 END")
    List<QuestionDto> findByTypeB(@Param("id") Integer id);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'c' THEN 1 WHEN 'a' THEN 2 WHEN 'b' THEN 3 END")
    List<QuestionDto> findByTypeC(@Param("id") Integer id);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.QuestionDto(q.explanation,q.choice1,q.choice2,q.choice3,q.choice4,q.choice5) FROM Question q where q.quizId = :id  ORDER BY CASE q.type WHEN 'a' THEN 1 WHEN 'b' THEN 2 WHEN 'c' THEN 3 END")
    List<QuestionDto> findByTypeA(@Param("id") Integer id);
}
