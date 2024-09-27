package com.moaguide.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status")
    int findlistTotal(@Param("status") String status);

    @Procedure(name = "startCount")
    int findstart(@Param("day")Date sqlDate);

    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status and pl.category = :category")
    int findlistTotalCategory(@Param("status") String status,@Param("category") String category);

    @Procedure(name = "startCountCategory")
    int findstartCategory(@Param("day") Date sqlDate, @Param("category") String category);

    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId JOIN Bookmark b ON b.productId.productId=p.productId WHERE pl.status = :status AND b.nickName = :nickname")
    int findlistTotalByBookmark(@Param("status") String status,@Param("nickname") String nickname);

    @Procedure(name = "startCountBookmark")
    int findstartBookmark(@Param("day")Date sqlDate,@Param("nickname") String nickname);
}

