package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class BusinessAreaDto {
    private String cbd;
    private String cbdDistance;
    private String cbdCar;
    private String cbdSubway;
    private String gbd;
    private String gbdDistance;
    private String gbdCar;
    private String gbdSubway;
    private String ybd;
    private String ybdDistance;
    private String ybdCar;
    private String ybdSubway;
    private Integer line;
    private Integer node;
}
