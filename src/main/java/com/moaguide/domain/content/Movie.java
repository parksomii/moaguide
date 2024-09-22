package com.moaguide.domain.content;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "movie")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "sub_Genre")
    private String subGenre;

    @Column(name = "release_Date")
    private Date releaseDate;

    @Column(name = "rating")
    private String rating;

    @Column(name = "screening_Time")
    private Integer screeningTime;

    @Column(name = "Director")
    private String director;

    @Column(name = "actor")
    private String actor;

    @Column(name = "distributor")
    private String distributor;

    @Column(name = "original")
    private String original;
}