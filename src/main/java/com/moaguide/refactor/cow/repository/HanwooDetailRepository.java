package com.moaguide.refactor.cow.repository;

import com.moaguide.refactor.cow.dto.HanwooBaseResponseDto;
import com.moaguide.refactor.cow.entity.HanwooDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HanwooDetailRepository extends JpaRepository<HanwooDetail, Long> {

	@Query(value = "CALL hanwoo_base(:productId)", nativeQuery = true)
	HanwooBaseResponseDto findHanwooDetail(@Param("productId") String productId);
}
