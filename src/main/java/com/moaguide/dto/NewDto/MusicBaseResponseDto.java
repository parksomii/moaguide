package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import com.moaguide.dto.NewDto.customDto.MusicDivideResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MusicBaseResponseDto {
// 발행정보
    private MusicPublishDto musicPublish;
// 곡 정보
    private MusicSongDto musicSong;
// 저작권료 정보
    private MusicDivideResponseDto musicDivide;
}
