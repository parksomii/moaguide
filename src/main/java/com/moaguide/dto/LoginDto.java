package com.moaguide.dto;

import com.moaguide.domain.user.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Slf4j
@ToString
@Setter
@AllArgsConstructor
public class LoginDto implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(user.getRole().name()));
        log.info("권한"+collection.toString());
        return collection;
    }

    @Override
    public String getPassword() {
        log.info("비밀번호"+user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("아이디"+user.getUserId());
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
