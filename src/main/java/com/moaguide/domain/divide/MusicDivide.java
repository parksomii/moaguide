package com.moaguide.domain.divide;

import com.moaguide.domain.summary.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Music_Divied")
public class MusicDivide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @Column(name="divide_day")
    private Date divideDay;


    private Double broadcasting;

    private Double transfer;

    private Double replication;

    private Double performance;

    private Double deum;

    private Double etc;

    public String getProductId() {
        return productId.getProductId();
    }
}
