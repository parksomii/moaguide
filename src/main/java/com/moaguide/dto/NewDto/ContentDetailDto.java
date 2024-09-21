package com.moaguide.dto.NewDto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
