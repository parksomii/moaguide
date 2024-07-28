package com.moaguide.domain.view;

import com.moaguide.domain.summary.Summary;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductViewId {
    private String localKey;
    private LocalDate day;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Summary productId;
}
