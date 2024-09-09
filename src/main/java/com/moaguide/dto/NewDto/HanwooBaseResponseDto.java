package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooBaseResponseDto {
    // 발행정보
    private HanwooPublishDto hanwooPublish;
    // 농가정보
    private HanwooFarmDto hanwooFarm;
}
