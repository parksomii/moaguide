package com.moaguide.dto.NewDto.BuildingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdDto {
    private int districId;
    private String product_Id;

    public IdDto(int districId){
        this.districId = districId;
    }
    public IdDto(String product_Id){
        this.product_Id = product_Id;
    }
}
