package com.moaguide.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custom_number;

    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
    private String password;
    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\\\d{3}|\\\\d{4})[.-]?(\\\\d{4})$")
    private String phone_number;
    private Role role;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    public User(String userId,String password,Role role){
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}