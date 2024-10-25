package com.moaguide.domain.building.stayday;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "Stay_Day")
public class StayDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String keyword;
    private Date day;
    private int noday;
    private int oneday;
    private int twoday;
    private int threeday;
    private int total;
    @Column(name = "noday_rate")
    private double nodayRate;
    @Column(name = "oneday_rate")
    private double onedayRate;
    @Column(name = "twoday_rate")
    private double twodayRate;
    @Column(name = "threeday_rate")
    private double threedayRate;
}
