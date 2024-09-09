package com.moaguide.domain.detail;

import com.moaguide.domain.building.districts.Districts;
import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingDetailDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SqlResultSetMapping(
        name = "buildingDetailDtoMapping",
        classes = @ConstructorResult(
                targetClass = BuildingReponseDto.class,
                columns = {
                        @ColumnResult(name = "productId", type = String.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "price", type = Integer.class),
                        @ColumnResult(name = "priceRate", type = Double.class),
                        @ColumnResult(name = "totalPrice", type = Long.class),
                        @ColumnResult(name = "dividend", type = Double.class),
                        @ColumnResult(name = "dividendRate", type = Double.class),
                        @ColumnResult(name = "divideCycle", type = Integer.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = "buildingDetail",
        procedureName = "building_detail",
        resultSetMappings = "buildingDetailDtoMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Product_Id", type = String.class)
        }
)
@Table(name = "Building_detail")
public class BuildingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

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
}