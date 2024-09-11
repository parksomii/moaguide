package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HanwooBaseResponseDto {
    // 발행정보
    private HanwooPublishDto publish;
    // 농가정보
    private HanwooFarmDto farm;

    public HanwooBaseResponseDto(HanwooPublishDto publishDto, HanwooFarmDto farm) {
        this.publish = publishDto;
        this.farm = farm;
    }
}
