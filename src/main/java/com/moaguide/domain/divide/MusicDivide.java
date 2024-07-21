package com.moaguide.domain.divide;

import com.moaguide.domain.summary.Summary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private Summary productId;

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
