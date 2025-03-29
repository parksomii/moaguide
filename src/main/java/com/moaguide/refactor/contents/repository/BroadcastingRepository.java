package com.moaguide.refactor.contents.repository;


import com.moaguide.refactor.contents.dto.BroadcastInfoDto;
import com.moaguide.refactor.contents.entity.Broadcasting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastingRepository extends JpaRepository<Broadcasting, Long> {


	@Query("SELECT new com.moaguide.refactor.contents.dto.BroadcastInfoDto(b.airDate,b.director,b.cast,b.company,b.channel) from Broadcasting b where b.productId.productId = :Id")
	BroadcastInfoDto findByProductId(@Param("Id") String productId);
}
