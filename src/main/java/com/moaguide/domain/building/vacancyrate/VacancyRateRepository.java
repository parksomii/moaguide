package com.moaguide.domain.building.vacancyrate;

import com.moaguide.domain.building.rent.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRateRepository extends JpaRepository<VacancyRate, Long> {

    @Query("SELECT v FROM VacancyRate v where v.keyword = :keyward AND v.type = :type")
    List<VacancyRate> findByLastmonth(@Param("keyward") String keyword,@Param("type") String type);
}
