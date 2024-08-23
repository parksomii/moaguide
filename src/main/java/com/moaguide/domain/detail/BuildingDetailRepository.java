package com.moaguide.domain.detail;


import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDetailRepository extends JpaRepository<BuildingDetail, Long> {

    @Query("SELECT b FROM BuildingDetail b WHERE b.productId.productId = :productId")
    BuildingDetail findByproductId(@Param("productId") String id);

    @Query("SELECT b FROM BuildingDetail b WHERE b.productId.name = :name")
    BuildingDetail findByProductName(@Param("name") String name);

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.IdDto(b.districtsId.districtsId) FROM BuildingDetail b WHERE b.productId.productId = :productId")
    IdDto findDistricId(@Param("productId") String productId);

    @Query("SELECT b FROM BuildingDetail b where b.keyword = :keyword")
    BuildingDetail findByKeyword(@Param("keyword")String keyword);
}
