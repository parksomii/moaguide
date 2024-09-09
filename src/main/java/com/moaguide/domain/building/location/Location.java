package com.moaguide.domain.building.location;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    public LocationDto toDto(){
        return new LocationDto(this.productId.getName(), this.longitude, this.latitude);
    };

}
