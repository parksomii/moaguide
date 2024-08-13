package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class codeDto {
    private String phone;
    private String code;
    private String nickname;

    public codeDto(String phone) {
        this.phone = phone;
    }
}
