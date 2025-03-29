package com.moaguide.refactor.contents.repository;


import com.moaguide.refactor.contents.dto.ExhibitInfoDto;
import com.moaguide.refactor.contents.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

	@Query("select new com.moaguide.refactor.contents.dto.ExhibitInfoDto(e.place,e.period) FROM Exhibition e where e.productId.productId=:Id")
	ExhibitInfoDto findByProductId(@Param("Id") String productId);
}
