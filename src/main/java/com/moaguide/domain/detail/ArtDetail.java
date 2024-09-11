package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "ArtDetail")
public class ArtDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "size", length = 50)
    private String size;    // 작품 크기(가로x세로)

    @Column(name = "production_date", length = 10)
    private String productionDate;  // 제작년도

    @Column(name = "material", length = 50)
    private String material;    // 재료

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate; // 거래일

    @Column(name = "auction_name", length = 50)
    private String auctionName;     // 경매명

    @Column(name = "place_entry", length = 50)
    private String placeEntry;      // 출품처

    @Column(name = "type", length = 30)
    private String type;        // 증권종류

    @Column(name = "issuer", length = 50)
    private String issuer;      // 발행인

    @Column(name = "issuance_date")
    private LocalDate issuanceDate;    // 청약기간

    @Column(name = "presumptive", length = 30)
    private String presumptive;     // 추정가
}
