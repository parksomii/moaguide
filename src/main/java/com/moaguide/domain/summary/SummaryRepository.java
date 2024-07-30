package com.moaguide.domain.summary;

import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, String> {

    @Override
    List<Summary> findAll();

    Summary findByProductId(String Id);
    // 카테고리별 상품 목록 조회
    @Query("select s from Summary s join Platform p ON s.PlatformId = p where p.category = :category")
    Page<Summary> findByCategory(@Param("category") String category, Pageable pageable);

    List<Summary> findTop2ByOrderByViewsDesc(Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s join Platform p ON s.PlatformId = p where p.category = :category ORDER BY s.views DESC")
    List<IdDto> findListByCategory(@Param("category")String category, Pageable pageable);

}