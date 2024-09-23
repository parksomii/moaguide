package com.moaguide.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT count(b) from Bookmark b where b.nickName.nickname=:nickname")
    int countByUser(@Param("nickname") String nickname);


    @Modifying
    @Query("DELETE FROM Bookmark b WHERE b.productId.productId = :productId AND b.nickName.nickname = :nickname")
    void deleteByProductIdAndNickName(@Param("productId") String productId, @Param("nickname") String nickname);

    @Modifying
    @Query(value = "INSERT INTO Bookmark (product_Id, nickname) VALUES (:productId, :nickname)", nativeQuery = true)
    void insertBookmark(@Param("productId") String productId, @Param("nickname") String nickname);

}
