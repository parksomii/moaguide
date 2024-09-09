package com.moaguide.domain.building.businessarea;


import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.BusinessAreaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Business_Area")
@SqlResultSetMapping(
        name = "BusinessAreaDtoMapping",
        classes = @ConstructorResult(
                targetClass = BusinessAreaDto.class,
                columns = {
                        @ColumnResult(name = "cbd", type = String.class),
                        @ColumnResult(name = "cbdDistance", type = String.class),
                        @ColumnResult(name = "cbdCar", type = String.class),
                        @ColumnResult(name = "cbdSubway", type = String.class),
                        @ColumnResult(name = "gbd", type = String.class),
                        @ColumnResult(name = "gbdDistance", type = String.class),
                        @ColumnResult(name = "gbdCar", type = String.class),
                        @ColumnResult(name = "gbdSubway", type = String.class),
                        @ColumnResult(name = "ybd", type = String.class),
                        @ColumnResult(name = "ybdDistance", type = String.class),
                        @ColumnResult(name = "ybdCar", type = String.class),
                        @ColumnResult(name = "ybdSubway", type = String.class),
                        @ColumnResult(name = "line", type = int.class),
                        @ColumnResult(name = "node", type = int.class),
                }
        )
)
@NamedStoredProcedureQuery(
        name = "BuildingsubProcedure",
        procedureName = "business",
        resultSetMappings = "BusinessAreaDtoMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class)
        }
)
public class BusinessArea {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "CBD")
    private String cbd;

    @Column(name = "CBD_Distance")
    private String cbdDistance;

    @Column(name = "CBD_Car")
    private String cbdCar;

    @Column(name = "CBD_Subway")
    private String cbdSubway;

    @Column(name = "GBD")
    private String gbd;

    @Column(name = "GBD_Distance")
    private String gbdDistance;

    @Column(name = "GBD_Car")
    private String gbdCar;

    @Column(name = "GBD_Subway")
    private String gbdSubway;

    @Column(name = "YBD")
    private String ybd;

    @Column(name = "YBD_Distance")
    private String ybdDistance;

    @Column(name = "YBD_Car")
    private String ybdCar;

    @Column(name = "YBD_Subway")
    private String ybdSubway;

}
