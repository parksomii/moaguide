package com.moaguide.domain.platform;

import com.moaguide.dto.PlatformDto;
import jakarta.persistence.*;
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