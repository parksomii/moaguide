package com.moaguide.dto;


import com.moaguide.refactor.product.entity.Platform;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlatformDto {
    private int PlatformId;
    private String category;
    private String platform;
    private String status;

    public Platform toEntity(){
        return new Platform(PlatformId, category, platform, status);
    }
}
