package com.moaguide.refactor.product.entity;

import com.moaguide.dto.PlatformDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Platform {
    @Id
    @Column(name="Platform_Id")
    private int PlatformId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String status;

    public PlatformDto toDto(){ return new PlatformDto(PlatformId, category, platform, status); }
}