package com.moaguide.dto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivideCustomDto {
    //최근 배당금
    private String product_Id;
    private String category;
    private String name;
    private int divideCycle;
}
