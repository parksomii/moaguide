package com.moaguide.dto.NewDto.oauth;

import com.moaguide.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        return null;
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
        return null;
    }

    public User getUser() {
        return user;
    }
}