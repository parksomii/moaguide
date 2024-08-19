package com.moaguide.domain.summary;

import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, String> {

    Summary findByProductId(String Id);
    // 상품현황
    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s where s.PlatformId.category = :category ORDER BY s.productId DESC")
    List<IdDto> findAllByCategory(@Param("category") String category, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s ORDER BY s.productId DESC")
    List<IdDto> findAllByList(PageRequest pageRequest);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s where s.PlatformId.category = :category ORDER BY s.views DESC")
    Page<IdDto>  findListByCategory(@Param("category")String category, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s where s.PlatformId.category = :category ORDER BY s.name ASC")
    Page<IdDto> findListByCategoryAndName(@Param("category")String category, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s ORDER BY s.views DESC")
    Page<IdDto> findListByAllbyViews(Pageable views);

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.IdDto(s.productId) from Summary s ORDER BY s.name ASC")
    Page<IdDto> findListByAllbyName(Pageable name);
}