package com.moaguide.domain.content.movie;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Movie_Showtimes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowtimes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    @Column(name = "showtimes_count")
    private int showtimesCount;

    private Date day;
}
