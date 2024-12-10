package com.moaguide.domain.billding;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;


@Entity
@Table(name = "Payment_Log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 로그 ID, 자동 증가

    private String nickname;

    @Column(name = "order_id")
    private String orderId;  // 결제 요청 ID

    @Column(name = "payment_key")
    private String paymentKey;  // Toss Payments 결제 Key

    @Column(name = "order_name")
    private String orderName;  // 결제 내역 이름

    @Column(name = "total_amount")
    private int totalAmount;  // 결제 금액

    @Column(name = "method")
    private String method;  // 결제 방식 (카드 또는 쿠폰)

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;  // 요청 시간

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;  // 승인 시간 (nullable)

    @Column(name = "discount")
    private int discount;  // 할인 금액

    public PaymentLog(String orderName, int totalAmount, String method, LocalDateTime requestedAt, LocalDateTime approvedAt, int discount,String nickname) {
        this.nickname = nickname;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.method = method;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.discount = discount;
    }

    public PaymentLog(String orderId,String nickname, String paymentKey, String orderName,Integer totalAmount, String card, LocalDateTime requestedAt, LocalDateTime approvedAt, int discount) {
        this.nickname = nickname;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.method = card;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.discount = discount;
    }
}