package com.moaguide.dto;

import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String phoneNumber;
    private Role role;
    private String nickname;
    private Date birthDate;
    private String investmentExperience;
    private Boolean marketingConsent;
    private String loginType;

    public User toUser() {
        return new User(
            this.email,
            this.name,
            this.password,
            this.phoneNumber,
            this.role,
            this.nickname,
            this.birthDate,
            this.investmentExperience,
            this.marketingConsent,
            this.loginType
        );
    }
}
