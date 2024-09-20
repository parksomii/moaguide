package com.moaguide.domain.content.movie;


import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Movie_Audience")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieAudience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    @Column(name = "audience_count")
    private int audienceCount;

    private Date day;
}
