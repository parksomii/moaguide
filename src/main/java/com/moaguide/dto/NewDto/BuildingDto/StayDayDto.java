package com.moaguide.dto.NewDto.BuildingDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
public class StayDayDto {
    private Date day;
    private int noday;
    private int oneday;
    private int twoday;
    private int threeday;
    private int total;
}
