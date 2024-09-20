package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.musicDto.ConsertDto;
import com.moaguide.dto.NewDto.musicDto.SearchDto;
import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import com.moaguide.dto.NewDto.musicDto.ViewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MusicSubResponseDto {
    private String youtubeUrl;  // 유튜브 url
    // 유튜브 조회수 리스트
    private List<ViewDto> youtubeViewCount; // 유튜브 조회수
    // 검색량 리스트
    private List<SearchDto> searchVolume;    // 검색량
    // 스트리밍 수 리스트
    private List<SteamingDto> streamingCount;  // 스트리밍 수
    // 공연일정 리스트
    private List<ConsertDto> consert;  // 공연일정
}
