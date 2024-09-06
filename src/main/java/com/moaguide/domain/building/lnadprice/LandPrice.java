package com.moaguide.domain.building.lnadprice;


import com.moaguide.domain.product.Product;
import com.moaguide.dto.LandPriceDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name="land_price")
public class LandPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @Column(name="land_price ")
    private int landPrice;

    @Column(name="base_year ")
    private String baseYear;

    @Column(name="base_day ")
    private String baseDay;

    @Column(name="notice_day ")
    private Date noticeDay;

    public LandPriceDto toDto(){
        return new LandPriceDto(this.landPrice,this.baseYear);
    }
}

