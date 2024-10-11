package com.moaguide.domain.content.movie;


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
    private Integer screenCount;

    private Date day;

    @Column(name = "showtimes_count")
    private int showtimesCount;

    private int revenue;

    @Column(name = "audience_count")
    private int audienceCount;

    private int ranking;
}
