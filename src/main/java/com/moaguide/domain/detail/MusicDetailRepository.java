package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.customDto.MusicReponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface MusicDetailRepository extends JpaRepository<MusicDetail, Long> {

    @Procedure(name = "music_detail")
    MusicReponseDto findMusicDetail(@Param("in_Product_Id") String productId);
}
