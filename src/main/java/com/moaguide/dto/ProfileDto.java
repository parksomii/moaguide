package com.moaguide.dto;

import lombok.*;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    public String nickname;
    public String email;
    public String loginType;
}
