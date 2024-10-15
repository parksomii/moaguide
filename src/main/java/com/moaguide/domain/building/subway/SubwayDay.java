package com.moaguide.domain.building.subway;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Subway_Day")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubwayDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String keyword;

    private Date day;

    private int boarding;

    private int alighting;
}
