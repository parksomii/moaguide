package com.moaguide.refactor.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Email_History")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailHistory {

	@Id
	private String email;
}