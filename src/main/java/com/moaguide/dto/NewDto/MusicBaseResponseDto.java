package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MusicBaseResponseDto {
// 주가
// 발행정보
    private MusicPublishDto musicPublishDto;
// 곡 정보
    private MusicSongDto musicSongDto;
// 저작권료 정보
    // 최근 1주당 저작권료
    // 최근 저작권료 수익률
    // 1년간 저작권료 수익률
    // 저작권료 상세정보
    // 저작권료 지급일
    // 저작권료 주기
// 지급주기별 저작권료 & 시가저작권료

}
