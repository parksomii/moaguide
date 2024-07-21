package com.moaguide.domain.building.area;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("SELECT A FROM Area A WHERE A.keyword = :productName")
    List<Area> findpolygon(@Param("productName")String name);
}
