package com.moaguide.dto.NewDto.oauth;

import com.moaguide.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final User user;


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return user.getNickname(); // 또는 다른 사용자 식별자를 반환하도록 변경 가능
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public String getRole() {
        return user.getRole().name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 반환
        return List.of(() -> user.getRole().name());
    }

    public User getUser() {
        return user;
    }
}