package com.moaguide.domain.content;


import com.moaguide.dto.NewDto.customDto.BroadcastInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastingRepository extends JpaRepository<Broadcasting, Long> {


    @Query("SELECT new com.moaguide.dto.NewDto.customDto.BroadcastINfoDto(b.airDate,b.director,b.cast,b.company,b.channel) from Broadcasting b where b.productId.productId = :Id")
    BroadcastInfoDto findByProductId(@Param("Id") String productId);
}
