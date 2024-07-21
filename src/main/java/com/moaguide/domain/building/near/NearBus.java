package com.moaguide.domain.building.near;

import com.moaguide.domain.summary.Summary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Near_Bus")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NearBus {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String keyword;

    private int line;
    private int node;
}
