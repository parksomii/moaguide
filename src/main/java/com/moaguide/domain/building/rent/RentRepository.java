package com.moaguide.domain.building.rent;


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

    @Query("SELECT distinct r.type FROM Rent r where r.keyword = :keyward")
    List<Optional<String>> findType(@Param("keyward")String keyword);
}
