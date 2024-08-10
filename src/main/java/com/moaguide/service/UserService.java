package com.moaguide.service;

import com.moaguide.domain.user.User;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> check(String nickName) {
        Optional<User> user = userRepository.findByNickname(nickName);
        return user;
    }

    @Transactional
    public int save(UserDto userdto) {
        try {
            userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
            userRepository.save(userdto.toUser());
            return 1;
        } catch (DataIntegrityViolationException e) {
            log.info(e.getMessage());
            return 0;
        } catch (Exception e) {
            log.info(e.getMessage());
            return 0;
        }
    }
}
