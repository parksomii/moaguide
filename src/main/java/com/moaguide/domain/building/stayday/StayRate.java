package com.moaguide.domain.building.stayday;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "Stay_Rate")
public class StayRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String keyword;
    private Date day;
    private Double rate;
    private Double value;
}
