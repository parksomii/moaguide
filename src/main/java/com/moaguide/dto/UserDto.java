package com.moaguide.dto;

import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import lombok.*;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long custom_number;
    private String email;
    private String name;
    private String password;
    private String phone_number;
    private Role role;
    private String userId;

    // 생성자
    public UserDto(String userId, String password, String name, String email, String phone_number) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public UserDto(User user) {
        this.custom_number = user.getCustom_number();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
    }

    public UserDto(String userId,String password) {
        this.userId = userId;
        this.password = password;
    }

    public User toEntity(){
        return new User(custom_number, email, name, password, phone_number, role, userId);
    }
}
