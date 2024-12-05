package com.moaguide.domain.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String cardname;
    private Integer cardNumber;

    @Column(name = "subscription_start_date")
    private Date subscriptionStartDate;

    @Column(name="subscription_end_date")
    private Date subscriptionEndDate;

    public Card(String nickname, String cardname, Integer cardNumber) {
        this.nickname = nickname;
        this.cardname = cardname;
        this.cardNumber = cardNumber;
    }

}
