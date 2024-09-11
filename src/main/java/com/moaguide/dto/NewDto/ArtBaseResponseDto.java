package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import com.moaguide.dto.NewDto.customDto.ArtPublishDto;
import com.moaguide.dto.NewDto.customDto.ArtWorkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtBaseResponseDto {
    private ArtPublishDto artPublish;
    private ArtAuthorDto artAuthor;
    private ArtWorkDto artWork;
}
