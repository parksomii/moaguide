package com.moaguide.refactor.product.repository;


import com.moaguide.refactor.product.entity.issueprice.IssuePrice;
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

    @Query("SELECT CEIL(count(i)/10.0) FROM IssuePrice i, Bookmark b WHERE b.productId.productId = i.id.productId.productId AND i.day > :day AND b.nickName.nickname = :nickname")
    int findissuebyBookcark(@Param("day") Date sqlDate,@Param("nickname")  String nickname);
}
