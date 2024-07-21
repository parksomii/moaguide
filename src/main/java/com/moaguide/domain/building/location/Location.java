package com.moaguide.domain.building.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.LocationDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Summary productId;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    public LocationDto toDto(){
        return new LocationDto(this.productId.getName(), this.longitude, this.latitude);
    };

}
