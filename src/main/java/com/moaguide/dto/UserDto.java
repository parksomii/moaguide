package com.moaguide.dto;

import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import lombok.*;

import java.sql.Date;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long customNumber;
    private String email;
    private String name;
    private String password;
    private Role role;
    private String nickname;
    private Date birthDate;
    private String investmentExperience;
    private Integer marketingConsent;
    private String loginType;

    public User toUser() {
        return new User(
            this.email,
            this.name,
            this.password,
            this.role,
            this.nickname,
            this.birthDate,
            this.investmentExperience,
            this.marketingConsent,
            this.loginType
        );
    }

    public UserDto(String email,String password) {
        this.email = email;
        this.password = password;
    }
}
