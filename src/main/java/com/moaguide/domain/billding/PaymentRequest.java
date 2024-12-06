package com.moaguide.domain.billding;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name = "Payment_Request")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @Id
    @Column(name = "order_id")
    private String orderId;

    private String nickname;

    private Integer amount;

    @Column(name = "next_payment_date")
    private Date NextPaymentDate;

    @Column(name = "fail_count")
    private Integer failCount;
}
