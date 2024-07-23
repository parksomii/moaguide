package com.moaguide.domain.detail;

import com.moaguide.domain.building.districts.Districts;
import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.BuildingDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "Building_detail")
public class BuildingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Summary productId;

    @ManyToOne
    @JoinColumn(name = "districts_id", referencedColumnName = "districts_Id")
    private Districts districtsId;

    @Column(name = "Use_area", length = 30)
    private String useArea;

    @Column(name = "main_use", length = 30)
    private String mainUse;

    @Column(name = "completion_date", length = 40)
    private String completionDate;

    @Column(name = "land_area", length = 50)
    private String landArea;

    @Column(name = "floor_area", length = 50)
    private String floorArea;

    @Column(name = "floor_area_rate")
    private Double floorAreaRate;

    @Column(name = "dry_ratio")
    private Double dryRatio;

    @Column(name = "Height")
    private Double height;

    @Column(name = "Scale", length = 40)
    private String scale;

    @Column(name = "Main_structure", length = 20)
    private String mainStructure;

    @Column(name = "parking")
    private Integer parking;

    @Column(name = "lift")
    private Integer lift;

    @Column(name = "Location", length = 50)
    private String location;

    @Column(name = "securities", length = 30)
    private String securities;

    @Column(name = "Publisher", length = 30)
    private String publisher;

    @Column(name = "Issuance_value")
    private Integer issuanceValue;

    @Column(name = "subscription_schedule", length = 50)
    private String subscriptionSchedule;

    @Column(name = "Listing_date")
    @Temporal(TemporalType.DATE)
    private Date listingDate;

    @Column(name = "Initial_dividend_base_date")
    @Temporal(TemporalType.DATE)
    private Date initialDividendBaseDate;

    @Column(name = "Dividend_cycle")
    private Integer dividendCycle;

    private String keyword;

    public BuildingDetailDto toDTO() {
        return new BuildingDetailDto(
                this.id,
                this.productId, // Summary 객체가 올바르게 처리될 수 있도록 이에 대한 처리가 필요할 수 있습니다.
                this.districtsId, // Districts 객체가 올바르게 처리될 수 있도록 이에 대한 처리가 필요할 수 있습니다.
                this.useArea,
                this.mainUse,
                this.completionDate,
                this.landArea,
                this.floorArea,
                this.floorAreaRate,
                this.dryRatio,
                this.height,
                this.scale,
                this.mainStructure,
                this.parking,
                this.lift,
                this.location,
                this.securities,
                this.publisher,
                this.issuanceValue,
                this.subscriptionSchedule,
                this.listingDate,
                this.initialDividendBaseDate,
                this.dividendCycle,
                this.keyword
        );

    }
}