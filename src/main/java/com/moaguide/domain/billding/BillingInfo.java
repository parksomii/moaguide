package com.moaguide.domain.billding;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Billing_Info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BillingInfo {

    @Id
    @Column(name = "customer_key")
    private String customerKey;

    @Column(name = "billing_key")
    private String billingKey;

    private String nickname;

    @Column(name = "authenticated_at")
    private Date authenticatedAt;

}
