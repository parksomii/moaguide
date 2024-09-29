package com.moaguide.dto.NewDto.customDto;


import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingTypeResponseDto {
    private BuildingReponseDto building;
    private List<TypeDto> rent;
}