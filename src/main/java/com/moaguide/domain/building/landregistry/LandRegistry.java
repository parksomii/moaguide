package com.moaguide.domain.building.landregistry;

import com.moaguide.domain.summary.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Land_Registry")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LandRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "Land_Elevation", length = 50)
    private String landElevation;

    @Column(name = "Land_Shape", length = 50)
    private String landShape;

    @Column(name = "Road_Interface", length = 50)
    private String roadInterface;

    @Column(name = "Zoning_National", columnDefinition = "TEXT")
    private String zoningNational;

    @Column(name = "Zoning_Other", columnDefinition = "TEXT")
    private String zoningOther;
}
