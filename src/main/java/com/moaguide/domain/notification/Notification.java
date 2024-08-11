package com.moaguide.domain.notification;

import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,referencedColumnName="user_nickname",name="user_nickname")
    private User nickName;
    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Summary productId;
    private String message;
    private LocalTime nowDate;
}
