package com.moaguide.service;


import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class signupService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public Long save(UserDto userDto) {
        userDto.setRole(Role.USER);
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        log.info("비밀번호 인코딩 후"+userDto);
        User user = repository.save(userDto.toEntity());
        return user.getCustom_number();
    }

    public String checkDuplicate(String userId) {
        User userfind = repository.findByUserId(userId).orElse(null);
        if (userfind == null) {
            // 중복안된 경우
            log.info("중복이 안되엇습니다.");
            return "available";
        } else {
            // 중복된 경우
            log.info("중복되었습니다.");
            return "duplicate";

        }
    }


}
