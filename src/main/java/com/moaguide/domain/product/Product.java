package com.moaguide.domain.product;

import com.moaguide.domain.platform.Platform;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.SummaryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
@Entity
@SqlResultSetMapping(
        name = "SummaryCustomDtoMapping",
        classes = @ConstructorResult(
                targetClass = SummaryCustomDto.class,
                columns = {
                        @ColumnResult(name = "productId", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "totalprice", type = Long.class),
                        @ColumnResult(name = "price", type = Integer.class),
                        @ColumnResult(name = "priceRate", type = Double.class),
                        @ColumnResult(name = "dividend", type = Double.class),
                        @ColumnResult(name = "dividendRate", type = Double.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "findCustomList",
        query = "CALL list(:page, :size, :sort)",
        resultSetMapping = "SummaryCustomDtoMapping"
)
public class Product {
    @Id
    @Column(name = "Product_Id")
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Platform_Id", referencedColumnName="Platform_Id")
    private Platform PlatformId;

    private String name;

    private Integer piece;

    private Integer views;

    @Column(name="now_piece")
    private Integer nowPiece;

    public String getCategory(){return PlatformId.getCategory();}
    public String getPlatform(){return PlatformId.getPlatform();}
    public SummaryDto toDto(){
        return new SummaryDto(productId, PlatformId, name, piece, views,nowPiece);
    }
}