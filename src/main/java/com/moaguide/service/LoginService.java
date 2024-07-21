package com.moaguide.service;


import com.moaguide.domain.user.User;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.LoginDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public LoginDto loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("유저id값을 잘 받는지"+username);
        User findId = userRepository.findByUserId(username).orElse(null);
        log.info("그 아이디 값으로 잘 엔티티를 찾았는지"+findId.toString());

        if (findId != null) {
            return new LoginDto(findId);
        }
        return null;
    }

    public String loginId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String loginRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        return auth.getAuthority();
    }



}
