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
        if (userdto.getLoginType().equals("local")) {
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
        } else {
            try {
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

    public User updateNickname(String findNickname, String changeNickname) {
        userRepository.updateNickname(findNickname, changeNickname);
        User user = userRepository.findUserByNickName(changeNickname);
        return user;
    }

    public boolean checkPassword(String nickname, String password) {
        User user = userRepository.findUserByNickName(nickname);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public void updatePassword(String nickname, String changePassword) {
        if(nickname.endsWith(".com")){
            userRepository.updatePasswordbyEmail(nickname, passwordEncoder.encode(changePassword));
        }else{
            userRepository.updatePasswordbyNickname(nickname, passwordEncoder.encode(changePassword));
        }
    }

    public String delete(String nickname) {
        try {
            userRepository.delete(userRepository.findUserByNickName(nickname));
            return "회원 탈퇴 성공";
        }catch (Exception e) {
            return "회원 탈퇴 실패";
        }
    }

    public boolean findDuplication(String email) {
        Optional<User> duplication = userRepository.findByEmailAndLoginType(email, "Local");
        return duplication.isPresent();
    }

    public String updateMarketing(String nickname, int status) {
        try {
            userRepository.updateMarketting(nickname,status);
            return "success";
        }catch (Exception e) {
            return "fail";
        }
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

}
