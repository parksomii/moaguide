package com.moaguide.domain.issueprice;

import com.moaguide.domain.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.io.Serializable;

public class IssuePriceId implements Serializable {

    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    @OneToOne
    private Product productId;
    private Integer issuePrice;
}
