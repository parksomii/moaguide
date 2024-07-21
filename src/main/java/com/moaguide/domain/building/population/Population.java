package com.moaguide.domain.building.population;

import com.moaguide.domain.building.districts.Districts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="population")
public class Population {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "districts_Id", nullable = true)
    private Districts districts;

    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "week_day")
    private String weekDay;

    private Integer total;

    private Integer age0;

    private Integer age10;

    private Integer age20;

    private Integer age30;

    private Integer age40;

    private Integer age50;

    private Integer age60;

    private Integer age70;

    private Integer man;

    private Integer girl;
}
