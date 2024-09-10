package com.moaguide.domain.building.landregistry;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Land_Registry")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SqlResultSetMapping(
        name = "BuildingBaseDtoMapping",
        classes = @ConstructorResult(
                targetClass = BuildingBaseDto.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "useArea", type = String.class),
                        @ColumnResult(name = "mainUse", type = String.class),
                        @ColumnResult(name = "completionDate", type = String.class),
                        @ColumnResult(name = "landArea", type = String.class),
                        @ColumnResult(name = "floorArea", type = String.class),
                        @ColumnResult(name = "floorAreaRate", type = Double.class),
                        @ColumnResult(name = "dryRatio", type = Double.class),
                        @ColumnResult(name = "height", type = Double.class),
                        @ColumnResult(name = "scale", type = String.class),
                        @ColumnResult(name = "mainStructure", type = String.class),
                        @ColumnResult(name = "parking", type = Integer.class),
                        @ColumnResult(name = "lift", type = Integer.class),
                        @ColumnResult(name = "location", type = String.class),
                        @ColumnResult(name = "landElevation", type = String.class),
                        @ColumnResult(name = "landShape", type = String.class),
                        @ColumnResult(name = "roadInterface", type = String.class),
                        @ColumnResult(name = "zoningNational", type = String.class),
                        @ColumnResult(name = "zoningOther", type = String.class),
                        @ColumnResult(name = "publisher", type = String.class),
                        @ColumnResult(name = "piece", type = Integer.class),
                        @ColumnResult(name = "lastDivide", type = Double.class),
                        @ColumnResult(name = "basePrice", type = Integer.class),
                        @ColumnResult(name = "totalPrice", type = long.class),
                        @ColumnResult(name = "subscription", type = String.class),
                        @ColumnResult(name = "listingDate", type = String.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = "BuildingBaseProcedure",
        procedureName = "building_base",
        resultSetMappings = "BuildingBaseDtoMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Product_Id", type = String.class)
        }
)
public class LandRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "Land_Elevation", length = 50)
    private String landElevation;

    @Column(name = "Land_Shape", length = 50)
    private String landShape;

    @Column(name = "Road_Interface", length = 50)
    private String roadInterface;

    @Column(name = "Zoning_National", columnDefinition = "TEXT")
    private String zoningNational;

    @Column(name = "Zoning_Other", columnDefinition = "TEXT")
    private String zoningOther;


    // 새로운 생성자 추가
    public LandRegistry(String landElevation, String landShape, String roadInterface, String zoningNational, String zoningOther) {
        this.landElevation = landElevation;
        this.landShape = landShape;
        this.roadInterface = roadInterface;
        this.zoningNational = zoningNational;
        this.zoningOther = zoningOther;
    }
}
