package com.moaguide.domain.bookmark;

import com.moaguide.domain.product.Product;
import com.moaguide.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,referencedColumnName="nickname",name="nickname")
    private User nickName;


    public Bookmark(String productId, String nickname) {
        this.productId = new Product();
        this.productId.setProductId(productId);
        this.nickName = new User();
        this.nickName.setNickname(nickname);
    }
}
