package com.moaguide.domain.building.lease;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_Id", nullable = false)
    private Product productId;

    private String tenant;
    @Column(name="Tenant_Introduction")
    private String tenantIntroduction;

    @Column(name="Lease_period")
    private String leasePeriod;

    @Column(name="Lease_area")
    private Double leaseArea;

    private Long deposit;

    private String rent;

    @Column(name="administration_cost")
    private String administrationCost;

    @Column(name="Detailed_conditions")
    private String detailedConditions;

}
