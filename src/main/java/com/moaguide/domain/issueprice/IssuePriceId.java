package com.moaguide.domain.issueprice;

import com.moaguide.domain.product.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter
public class IssuePriceId implements Serializable {

    @OneToOne
    @JoinColumn(name = "product_Id", referencedColumnName = "product_Id")
    private Product productId;  // 여기서 Product 객체를 통해 productId에 접근합니다.
    private Integer issuePrice;
}
