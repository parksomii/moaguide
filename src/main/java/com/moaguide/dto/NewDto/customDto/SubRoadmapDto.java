package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRoadmapDto {
    private Long id;

    private Integer number;

    private String title;

    private String description;
}
