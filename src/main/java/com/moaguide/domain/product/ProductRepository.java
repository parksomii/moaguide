package com.moaguide.domain.product;

import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status")
    int findlistTotal(@Param("status") String status);

    @Procedure(name = "startCount")
    int findstart(@Param("day")Date sqlDate);

    @Query("SELECT CEIL(count(p)/10) FROM Product p JOIN Platform pl ON pl.PlatformId = p.PlatformId.PlatformId WHERE pl.status = :status and pl.category = :categroy")
    int findlistTotalCategory(@Param("status") String status,@Param("category") String category);

    @Procedure(name = "startCountCategory")
    int findstartCategory(@Param("day") Date sqlDate, @Param("category") String category);
}

