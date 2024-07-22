package com.moaguide.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NearSubwayDto {
    private String station;

    private String route;

    private int distance;

    private int time;
}