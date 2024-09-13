package com.moaguide.domain.issueprice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface IssuePriceRepository extends JpaRepository<IssuePrice, Long> {

    @Query("SELECT CEIL(count(i)/10.0) FROM IssuePrice i where i.day > :day ")
    int findissue(@Param("day") Date sqlDate);

    @Procedure(name = "Issue_count_category")
    int findissuebyCategroy(@Param("day") Date sqlDate,@Param("category") String category);
}
