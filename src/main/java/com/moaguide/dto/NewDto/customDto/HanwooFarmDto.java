package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.detail.HanwooDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class HanwooFarmDto {
    private String certificationNumber; // 인증번호
    private String certificationAgency; // 인증기관
    private String manager; // 관리책임자
    private String certifiedHeads; // 인증두수
    private String cattleBreed; // 축종
    private LocalDate initialDate; // 최초인증일

    public HanwooFarmDto(String certificationNumber, String certificationAgency, String manager,
                         String certifiedHeads, String cattleBreed, LocalDate initialDate) {
        this.certificationNumber = certificationNumber;
        this.certificationAgency = certificationAgency;
        this.manager = manager;
        this.certifiedHeads = certifiedHeads;
        this.cattleBreed = cattleBreed;
        this.initialDate = initialDate;
    }
}
