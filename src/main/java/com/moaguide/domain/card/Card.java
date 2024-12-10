package com.moaguide.domain.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

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
    @Column(name = "card_name")
    private String cardname;
    @Column(name = "card_number")
    private Integer cardNumber;

    @Column(name = "subscription_start_date")
    private LocalDate subscriptionStartDate;

    @Column(name="subscription_end_date")
    private LocalDate subscriptionEndDate;

    public Card(String nickname, String cardname, Integer cardNumber) {
        this.nickname = nickname;
        this.cardname = cardname;
        this.cardNumber = cardNumber;
    }

}
