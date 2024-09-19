package com.moaguide.domain.content;


import com.moaguide.dto.NewDto.customDto.TravelInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.TravelInfoDto(t.place,t.openDate,t.Structure,t.Production) FROM Travel t where t.productId.productId=:Id")
    TravelInfoDto findByProductId(@Param("Id") String productId);
}
