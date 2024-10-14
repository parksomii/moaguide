package com.moaguide.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status")
    int findlistTotal(@Param("status") String status);

    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status and pl.category = :category")
    int findlistTotalCategory(@Param("status") String status,@Param("category") String category);

    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId JOIN Bookmark b ON b.productId.productId=p.productId WHERE pl.status = :status AND b.nickName.nickname = :nickname")
    int findlistTotalByBookmark(@Param("status") String status,@Param("nickname") String nickname);

    @Modifying
    @Query("update Product p set p.views = p.views + 1 where p.productId = :productId")
    void updateByProductId(@Param("productId") String productId);
}

