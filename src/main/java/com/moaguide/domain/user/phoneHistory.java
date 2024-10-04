package com.moaguide.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class phoneHistory {

    @Id
    @Column(name="phone_number")
    private String phoneNumber;
}
