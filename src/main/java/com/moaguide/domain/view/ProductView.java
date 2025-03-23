package com.moaguide.domain.view;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Table(name = "Product_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nickname을 외래 키로 설정
    @Column(name = "nickname", nullable = false)
    private String nickname;

    // Product_Id를 외래 키로 설정
    @Column(name = "Product_Id", nullable = false)
    private String productId;

    @Column(name = "access_time")
    private Timestamp accessTime;

    public ProductView(String nickname, String productId, Timestamp accessTime) {
        this.nickname = nickname;
        this.productId = productId;
        this.accessTime = accessTime;
    }


}
