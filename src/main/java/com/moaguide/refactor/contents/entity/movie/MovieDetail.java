package com.moaguide.refactor.contents.entity.movie;


import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "Movie_Detail")
public class MovieDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    @Column(name = "screen_count")
    private Long screenCount;

    private Date day;

    @Column(name = "showtimes_count")
    private Long showtimesCount;

    private Long revenue;

    @Column(name = "audience_count")
    private Long audienceCount;

    private Long ranking;
}
