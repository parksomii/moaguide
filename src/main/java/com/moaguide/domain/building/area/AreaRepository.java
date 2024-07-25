package com.moaguide.domain.building.area;


import com.moaguide.dto.NewDto.BuildingDto.AreaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.AreaDto(A.areaSize,A.productName,A.polygon,A.latitude,A.longitude,A.color) FROM Area A WHERE A.keyword = :keyword")
    List<AreaDto> findpolygon(@Param("keyword")String name);
}
