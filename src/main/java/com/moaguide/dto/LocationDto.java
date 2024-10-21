package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LocationDto {

    private String name;

    private double longitude;

    private double latitude;

    private String location;
}
