package com.moaguide.refactor.building.repository;

import com.moaguide.dto.NewDto.BuildingDto.LeaseDto;
import com.moaguide.refactor.building.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.LeaseDto(L.tenant,L.tenantIntroduction,L.leasePeriod,L.leaseArea,L.deposit,L.rent,L.administrationCost,L.detailedConditions) FROM Lease L WHERE L.productId.productId = :productId")
    List<LeaseDto> findByproductId(@Param("productId") String id);
}
