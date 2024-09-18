package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicDivideDto {
    private Double broadcasting; // 방송
    private Double transfer;    // 전송
    private Double replication; // 복제
    private Double performance; // 공연
    private Double deum;        // 디음
    private Double etc;         // 기타
}
