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

    @Column(name = "year")
    private int Year;
    @Column(name = "months")
    private int Month;

    @Column(name = "boarding_05_06")
    private int boarding05;

    @Column(name = "alighting_05_06")
    private int alighting05;

    @Column(name = "boarding_06_07")
    private int boarding06;

    @Column(name = "alighting_06_07")
    private int alighting06;

    @Column(name = "boarding_07_08")
    private int boarding07;

    @Column(name = "alighting_07_08")
    private int alighting07;

    @Column(name = "boarding_08_09")
    private int boarding08;

    @Column(name = "alighting_08_09")
    private int alighting08;

    @Column(name = "boarding_09_10")
    private int boarding09;

    @Column(name = "alighting_09_10")
    private int alighting09;

    @Column(name = "boarding_10_11")
    private int boarding10;

    @Column(name = "alighting_10_11")
    private int alighting10;

    @Column(name = "boarding_11_12")
    private int boarding11;

    @Column(name = "alighting_11_12")
    private int alighting11;

    @Column(name = "boarding_12_13")
    private int boarding12;

    @Column(name = "alighting_12_13")
    private int alighting12;

    @Column(name = "boarding_13_14")
    private int boarding13;

    @Column(name = "alighting_13_14")
    private int alighting13;

    @Column(name = "boarding_14_15")
    private int boarding14;

    @Column(name = "alighting_14_15")
    private int alighting14;

    @Column(name = "boarding_15_16")
    private int boarding15;

    @Column(name = "alighting_15_16")
    private int alighting15;

    @Column(name = "boarding_16_17")
    private int boarding16;

    @Column(name = "alighting_16_17")
    private int alighting16;

    @Column(name = "boarding_17_18")
    private int boarding17;

    @Column(name = "alighting_17_18")
    private int alighting17;

    @Column(name = "boarding_18_19")
    private int boarding18;

    @Column(name = "alighting_18_19")
    private int alighting18;

    @Column(name = "boarding_19_20")
    private int boarding19;

    @Column(name = "alighting_19_20")
    private int alighting19;

    @Column(name = "boarding_20_21")
    private int boarding20;

    @Column(name = "alighting_20_21")
    private int alighting20;

    @Column(name = "boarding_21_22")
    private int boarding21;

    @Column(name = "alighting_21_22")
    private int alighting21;

    @Column(name = "boarding_22_23")
    private int boarding22;

    @Column(name = "alighting_22_23")
    private int alighting22;

    @Column(name = "boarding_23_24")
    private int boarding23;

    @Column(name = "alighting_23_24")
    private int alighting23;
}
