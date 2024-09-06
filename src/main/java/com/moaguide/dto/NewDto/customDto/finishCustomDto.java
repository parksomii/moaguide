package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class finishCustomDto {
    private String product_Id;
    private String name;
    private String category;
    private String platform;
    private double sail_rate;
    private long totalprice;
}
