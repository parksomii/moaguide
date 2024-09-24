package com.moaguide.domain.user;

import jakarta.persistence.*;
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
