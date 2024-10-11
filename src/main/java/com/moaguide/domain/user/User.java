package com.moaguide.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Entity
@Setter
@Table(name = "User")
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

    public User(String email, String name, String password, Role role, String nickname, Date birthDate, String investmentExperience, Boolean marketingConsent, String loginType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.investmentExperience = investmentExperience;
        this.marketingConsent = marketingConsent;
        this.loginType = loginType;
    }

    public User(String email,String registrationId) {
        this.email = email;
        this.loginType = registrationId;
    }
}
