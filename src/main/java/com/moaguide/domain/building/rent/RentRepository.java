package com.moaguide.domain.building.rent;


import com.moaguide.dto.NewDto.TypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query("SELECT r FROM Rent r where r.keyword = :keyward AND r.type = :type")
    List<Rent> findBytype(@Param("keyward") String keyword,@Param("type") String type);

    @Query("SELECT DISTINCT new com.moaguide.dto.NewDto.TypeDto(r.type) FROM Rent r WHERE r.keyword = :keyword")
    List<TypeDto> findType(@Param("keyward")String keyword);
}
