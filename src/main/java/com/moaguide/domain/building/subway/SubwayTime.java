package com.moaguide.domain.building.subway;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Subway_Time")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubwayTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String keyword;

    private int Year;

    private int Month;

    @Column(name = "boarding_05_06")
    private int boarding_05;

    @Column(name = "alighting_05_06")
    private int alighting_05;

    @Column(name = "boarding_06_07")
    private int boarding_06;

    @Column(name = "alighting_06_07")
    private int alighting_06;

    @Column(name = "boarding_07_08")
    private int boarding_07;

    @Column(name = "alighting_07_08")
    private int alighting_07;

    @Column(name = "boarding_08_09")
    private int boarding_08;

    @Column(name = "alighting_08_09")
    private int alighting_08;

    @Column(name = "boarding_09_10")
    private int boarding_09;

    @Column(name = "alighting_09_10")
    private int alighting_09;

    @Column(name = "boarding_10_11")
    private int boarding_10;

    @Column(name = "alighting_10_11")
    private int alighting_10;

    @Column(name = "boarding_11_12")
    private int boarding_11;

    @Column(name = "alighting_11_12")
    private int alighting_11;

    @Column(name = "boarding_12_13")
    private int boarding_12;

    @Column(name = "alighting_12_13")
    private int alighting_12;

    @Column(name = "boarding_13_14")
    private int boarding_13;

    @Column(name = "alighting_13_14")
    private int alighting_13;

    @Column(name = "boarding_14_15")
    private int boarding_14;

    @Column(name = "alighting_14_15")
    private int alighting_14;

    @Column(name = "boarding_15_16")
    private int boarding_15;

    @Column(name = "alighting_15_16")
    private int alighting_15;

    @Column(name = "boarding_16_17")
    private int boarding_16;

    @Column(name = "alighting_16_17")
    private int alighting_16;

    @Column(name = "boarding_17_18")
    private int boarding_17;

    @Column(name = "alighting_17_18")
    private int alighting_17;

    @Column(name = "boarding_18_19")
    private int boarding_18;

    @Column(name = "alighting_18_19")
    private int alighting_18;

    @Column(name = "boarding_19_20")
    private int boarding_19;

    @Column(name = "alighting_19_20")
    private int alighting_19;

    @Column(name = "boarding_20_21")
    private int boarding_20;

    @Column(name = "alighting_20_21")
    private int alighting_20;

    @Column(name = "boarding_21_22")
    private int boarding_21;

    @Column(name = "alighting_21_22")
    private int alighting_21;

    @Column(name = "boarding_22_23")
    private int boarding_22;

    @Column(name = "alighting_22_23")
    private int alighting_22;

    @Column(name = "boarding_23_24")
    private int boarding_23;

    @Column(name = "alighting_23_24")
    private int alighting_23;

}
