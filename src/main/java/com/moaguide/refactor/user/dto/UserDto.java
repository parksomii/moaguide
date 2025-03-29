package com.moaguide.refactor.user.dto;

import com.moaguide.refactor.enums.Role;
import com.moaguide.refactor.user.entity.User;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

	public UserDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
