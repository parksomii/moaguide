package com.moaguide.refactor.contents.entity;


import com.moaguide.refactor.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Broadcasting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    @Column(name="first_air_date")
    private Date airDate;
    private String director;
    private String cast;
    @Column(name = "production_company")
    private String company;
    @Column(name="broadcast_channel")
    private String channel;
}
