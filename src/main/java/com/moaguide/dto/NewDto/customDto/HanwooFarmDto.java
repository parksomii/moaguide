package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HanwooFarmDto {
    private String certificationNumber; // 인증번호
    private String certificationAgency; // 인증기관
    private String manager; // 관리책임자
    private String certifiedHeads; // 인증두수
    private String cattleBreed; // 축종
    private String initialDate; // 최초인증일
}
