package com.moaguide.refactor.user.entity;

import com.moaguide.refactor.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "investment_experience")
	private String investmentExperience;

	@Column(name = "marketing_consent")
	private Integer marketingConsent;

	@Column(name = "loginType")
	private String loginType;


	@Column(name = "card_name")
	private String cardname;

	@Column(name = "card_number")
	private Integer cardNumber;

	@Column(name = "subscription_start_date")
	private LocalDate subscriptionStartDate;

	@Column(name = "subscription_end_date")
	private LocalDate subscriptionEndDate;

	public User(String email, String name, String password, Role role, String nickname,
		Date birthDate, String investmentExperience, Integer marketingConsent, String loginType) {
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

	public User(String email, String registrationId) {
		this.email = email;
		this.loginType = registrationId;
	}

}
