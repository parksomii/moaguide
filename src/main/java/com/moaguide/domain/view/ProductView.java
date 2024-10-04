package com.moaguide.domain.view;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Product_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductView{

    @EmbeddedId
    private ProductViewId id;

}
