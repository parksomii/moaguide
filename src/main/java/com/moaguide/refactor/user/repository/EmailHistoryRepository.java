package com.moaguide.refactor.user.repository;

import com.moaguide.refactor.user.entity.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistory, String> {

}