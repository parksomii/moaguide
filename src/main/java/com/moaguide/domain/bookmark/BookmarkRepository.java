package com.moaguide.domain.bookmark;

import com.moaguide.dto.NewDto.customDto.BookmarkProductDto;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT CEIL(count(b)/10) from Bookmark b where b.nickName.nickname=:nickname")
    int countByUser(@Param("nickname") String nickname);


    @Modifying
    @Query("DELETE FROM Bookmark b WHERE b.productId.productId = :productId AND b.nickName.nickname = :nickname")
    void deleteByProductIdAndNickName(@Param("productId") String productId, @Param("nickname") String nickname);

    @Modifying
    @Query(value = "INSERT INTO Bookmark (product_Id, nickname) VALUES (:productId, :nickname)", nativeQuery = true)
    void insertBookmark(@Param("productId") String productId, @Param("nickname") String nickname);

    @Query("select count(b) FROM Bookmark b where b.nickName.nickname=:nickname")
    int getTotal(@Param("nickname") String nickname);

    @Procedure(name = "bookmarkProductProcedure")
    List<BookmarkProductDto> getAllbookmark(@Param("page")int page, @Param("size")int size, @Param("nickname")String nickname);

    @Procedure(name = "bookmarkProductCategoryProcedure")
    List<BookmarkProductDto> getAllbookmarkBycategory(@Param("page")int page, @Param("size")int size, @Param("nickname")String nickname,@Param("category") String category);


    @Query(name = "getbookmarkCategoryCount", nativeQuery = true)
    int getTotalBycategory(@Param("nickname") String nickname, @Param("category") String category);
}
