package com.moaguide.dto;


import com.moaguide.domain.platform.Platform;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlatformDto {
    private int PlatformId;
    private String category;
    private String platform;
    private boolean passible;

    public Platform toEntity(){
        return new Platform(PlatformId, category, platform, passible);
    }
}
