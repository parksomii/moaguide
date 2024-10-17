package com.moaguide.domain.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
    @Id
    private Long id;
    private String nickName;
    @Column(name = "Product_Id")
    private String productId;
    private String message;
    private Date nowDate;
}
