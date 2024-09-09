package com.moaguide.domain.issueprice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuePriceRepository extends JpaRepository<IssuePrice, Long> {

}
