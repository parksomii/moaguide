package com.moaguide.dto.NewDto.customDto;

import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MaxAndMinDto {
    private List<SteamingDto> steamingDto;
    private int max;
    private int min;
}
