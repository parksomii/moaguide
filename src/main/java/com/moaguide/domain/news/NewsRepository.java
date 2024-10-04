package com.moaguide.domain.news;

import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // 페이지네이션을 이용해 keyword로 검색는 메소드
    Page<News> findByKeyword(String keyword, Pageable pageable);

    // 조회수 올리는 메소드
    @Modifying
    @Transactional
    @Query("UPDATE News n SET n.views = n.views + 1 WHERE n.id = :id")
    void updateViewCount(@Param("id") Long id);

    // 뉴스 링크를 가져오는 메소드
    @Query("SELECT n.link FROM News n WHERE n.id = :id")
    String getNewsLink(@Param("id") Long id);

    List<News> findTop2ByOrderByDateDesc(Pageable pageable);

    // 많이 본 뉴스
    @Query("SELECT n FROM News n ORDER BY n.views DESC")
    Page<News> findTop3ByOrderByViewsDesc(Pageable pageable);

    // 최신 뉴스
    @Query("SELECT n FROM News n ORDER BY n.date DESC")
    Page<News> findLatest(Pageable pageable);

    // 카테고리별 뉴스 조회 + 최신순, 인기순
    @Query("SELECT n FROM News n WHERE n.category = :category ORDER BY n.date DESC")
    Page<News> findAllByCategory(Pageable pageable, @Param("category") String category);

    @Query(name="getNewsCount", nativeQuery=true)
    Integer findByDetailCount(@Param("productId") String productId);


    @Query(name = "getBykeyword", nativeQuery = true)
    List<NewsCustomDto> findBydetail(@Param("productId")String productId,@Param("page") int page,@Param("size") int size);
}
