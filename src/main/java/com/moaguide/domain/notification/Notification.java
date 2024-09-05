package com.moaguide.domain.notification;

import com.moaguide.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,referencedColumnName="nickname",name="nickname")
    private User nickName;
    private String productId;
    private String message;
    private LocalTime nowDate;
}
