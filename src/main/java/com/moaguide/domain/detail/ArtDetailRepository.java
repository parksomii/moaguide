package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.customDto.ArtPublishDto;
import com.moaguide.dto.NewDto.customDto.ArtWorkDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtDetailRepository extends JpaRepository<ArtDetail, Long> {

    @Query(value = "SELECT ArtPublishDto(a.productId, a.size, a.productionDate, a.material, a.transactionDate," +
            " a.auctionName, a.placeEntry, a.type, a.issuer, a.subscriptionDate, a.presumptive) FROM ArtDetail a WHERE a.productId = :productId")
    ArtPublishDto findArtDetail(@Param("productId") String productId);

    @Query(value = "SELECT ArtWorkDto(a.productId, a.size, a.productionDate, a.material, a.transactionDate," +
            " a.auctionName, a.placeEntry, a.type, a.issuer, a.subscriptionDate, a.presumptive) FROM ArtDetail a WHERE a.productId = :productId")
    ArtWorkDto findWorkDetail(@Param("productId") String productId);
}
