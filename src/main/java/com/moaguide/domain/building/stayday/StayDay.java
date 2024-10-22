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
}
