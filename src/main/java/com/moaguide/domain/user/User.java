package com.moaguide.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;

@Getter
@Entity
@Setter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_number")
    private Long customNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "investment_experience")
    private String investmentExperience;

    @Column(name = "marketing_consent")
    private Boolean marketingConsent;

    @Column(name = "loginType")
    private String loginType;

    public User(String email, String name, String password, String phoneNumber, Role role, String nickname, Date birthDate, String investmentExperience, Boolean marketingConsent, String loginType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.investmentExperience = investmentExperience;
        this.marketingConsent = marketingConsent;
        this.loginType = loginType;
    }
}
