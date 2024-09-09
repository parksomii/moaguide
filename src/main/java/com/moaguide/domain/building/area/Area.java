package com.moaguide.domain.building.area;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Area")
public class Area {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "area_size")
    private int areaSize;

    @Column(name="area_name")
    private String areaName;

    private String polygon;

    private String keyword;

    private double latitude;

    private double longitude;

    private String color;
}
