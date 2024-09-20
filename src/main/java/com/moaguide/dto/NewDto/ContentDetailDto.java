package com.moaguide.dto.NewDto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMapping(
        name = "ContentDetailDtoMapping",
        classes = @ConstructorResult(
                targetClass = ContentDetailDto.class,
                columns = {
                        @ColumnResult(name = "productId", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "genre", type = String.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class),
                        @ColumnResult(name = "totalPrice", type = long.class),
                        @ColumnResult(name = "rate", type = Double.class),
                        @ColumnResult(name = "date", type = Date.class),
                        @ColumnResult(name = "lowPrice", type = Integer.class)
                }
        )
)
public class ContentDetailDto {
    private String productId;
    private String name;
    private String genre;
    private String category;
    private String platform;
    private long totalPrice;
    private Double rate;
    private Date date;
    private Integer lowPrice;
}
