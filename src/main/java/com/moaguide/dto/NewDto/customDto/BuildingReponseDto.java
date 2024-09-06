package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BuildingReponseDto {
    private String Product_Id;
    private String category;
    private String Platform;
    private String Name;
    private long price;
    private double price_rate;
    private String total;
    private double dividend;
    private double dividend_Rate;
    private int divide_cycle;
}
