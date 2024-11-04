package com.moaguide.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuzeHistoryRepository extends JpaRepository<QuzeHistory, Long> {
}
