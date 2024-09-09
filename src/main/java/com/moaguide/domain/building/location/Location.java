package com.moaguide.domain.building.location;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.BusinessAreaDto;
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
        name = "AreaDtoMapping",
        classes = @ConstructorResult(
                targetClass = LocationDto.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "Longitude", type = double.class),
                        @ColumnResult(name = "Latitude", type = double.class),
                }
        )
)
@NamedStoredProcedureQuery(
        name = "AreaProcedure",
        procedureName = "locate",
        resultSetMappings = "AreaDtoMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class)
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
