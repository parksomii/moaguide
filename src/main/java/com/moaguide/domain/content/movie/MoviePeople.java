package com.moaguide.domain.content.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="Movie_People")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MoviePeople {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "movie", nullable = false)
    private String movie;

    private String role;

    private String side;

    @Column(name = "official_money")
    private Long officialMoney;

    @Column(name = "official_people")
    private Long officialPeople;

    private Long money;

    private Long people;
}
