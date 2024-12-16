package com.moaguide.domain.billing.localbilling;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Table(name = "Local_Payment_Request")
@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Profile("local")
public class LocalPaymentRequest {
    @Id
    @Column(name = "order_id")
    private String orderId;

    private String nickname;

    private Integer amount;

    @Column(name = "next_payment_date")
    private LocalDateTime NextPaymentDate;

    @Column(name = "fail_count")
    private Integer failCount;
}
