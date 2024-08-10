package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class codeDto {
    private String phone;
    private String code;

    public codeDto(String phone) {
        this.phone = phone;
    }
}
