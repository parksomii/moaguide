package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDetailDto {
    private String productId;
    private String name;
    private String genre;
    private String category;
    private String platform;
    private Long totalPrice;
    private double rate;
    private Date date;
    private Int lowPrice;
}
