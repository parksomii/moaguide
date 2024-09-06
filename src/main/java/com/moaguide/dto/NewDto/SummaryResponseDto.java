package com.moaguide.dto.NewDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDto {
    private List<?> product;
    private int page;
    private int size;
}
