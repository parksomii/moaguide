package com.moaguide.refactor.music.repository;

import com.moaguide.refactor.music.dto.MusicReponseDto;
import com.moaguide.refactor.music.dto.MusicSubResponseDto;
import com.moaguide.refactor.music.entity.MusicDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface MusicDetailRepository extends JpaRepository<MusicDetail, Long> {

	// music_detail 프로시저를 호출
	@Procedure(name = "music_detail")
	MusicReponseDto findMusicDetail(@Param("in_Product_Id") String productId,
		@Param("nickname") String nickname, @Param("year") int year);

	@Query("SELECT new com.moaguide.dto.NewDto.MusicSubResponseDto(md.youtubeUrl, p.name) " +
		"FROM MusicDetail md, Product p " +
		"WHERE md.productId.productId = p.productId and md.productId.productId = :productId")
	MusicSubResponseDto findYoutube(@Param("productId") String productId);
}
