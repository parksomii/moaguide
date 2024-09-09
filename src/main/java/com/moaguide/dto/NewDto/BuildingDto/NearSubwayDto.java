package com.moaguide.dto.NewDto.BuildingDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class NearSubwayDto {
    private String station;

    private String route;

    private int distance;

    private int time;
}