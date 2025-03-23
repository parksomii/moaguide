package com.moaguide.refactor.cow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "grade1Rate")
public class Grade1Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @Column(name = "c_type", length = 30)
    private String cType; // 분류값 명3

    @Column(name = "prd_de")
    private LocalDate prdDe; // 수록 시점

    @Column(name = "prd_dt")
    private Double prdDt; // 수치값
}
