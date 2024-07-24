package com.moaguide.domain.building.lease;

import com.moaguide.dto.NewDto.LeaseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.LeaseDto(L.tenant,L.tenantIntroduction,L.leasePeriod,L.leaseArea,L.deposit,L.rent,L.administrationCost,L.detailedConditions) FROM Lease L WHERE L.productId.productId = :productId")
    List<LeaseDto> findByproductId(@Param("productId") String id);
}
