package com.moaguide.domain.building.subway;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Subway_Week")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubwayWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String keyword;

    private int Year;

    private int Month;

    @Column(name="day_of_week")
    private String weekDay;

    @Column(name="total_boarding")
    private int totalBoarding;

    @Column(name="total_alighting")
    private int totalAlighting;

}
