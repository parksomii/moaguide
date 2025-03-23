package com.moaguide.refactor.notice.entity;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;
    private String title;
    private String content;
    private LocalDate date;

}
