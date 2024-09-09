package com.moaguide.domain.building.subway;

import com.moaguide.dto.NewDto.BuildingDto.SubwayTimeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayTimeRepository extends JpaRepository<SubwayTime, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.SubwayTimeDto(s.Year, s.Month, s.boarding05, s.alighting05, s.boarding06, s.alighting06, s.boarding07, s.alighting07, s.boarding08, s.alighting08, s.boarding09, s.alighting09, s.boarding10, s.alighting10, s.boarding11, s.alighting11, s.boarding12, s.alighting12, s.boarding13, s.alighting13, s.boarding14, s.alighting14, s.boarding15, s.alighting15, s.boarding16, s.alighting16, s.boarding17, s.alighting17, s.boarding18, s.alighting18, s.boarding19, s.alighting19, s.boarding20, s.alighting20, s.boarding21, s.alighting21, s.boarding22, s.alighting22, s.boarding23, s.alighting23) FROM SubwayTime s, BuildingDetail bd where s.keyword = bd.keyword AND s.Year = :year AND s.Month = :month AND bd.productId.productId = :product_Id ")
    SubwayTimeDto findByLastmonth(@Param("product_Id") String productId, @Param("year") int year, @Param("month")int month);
}
