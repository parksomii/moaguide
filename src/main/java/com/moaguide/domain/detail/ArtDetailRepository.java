package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ArtDetailRepository extends JpaRepository<ArtDetail, Long> {

    @Procedure(name = "art_base")
    ArtBaseResponseDto findArtBaseDetail(@Param("in_Product_Id") String productId);
}
