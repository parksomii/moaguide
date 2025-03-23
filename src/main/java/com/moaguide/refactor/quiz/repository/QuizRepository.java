package com.moaguide.refactor.quiz.repository;

import com.moaguide.refactor.quiz.entity.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("select q FROM Quiz q order by q.id desc")
    List<Quiz> findQuiz(Pageable pageable);
}
