package com.moaguide.domain.quiz;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("select q FROM Quiz q order by q.id desc")
    Quiz findQuiz(Pageable pageable);
}
