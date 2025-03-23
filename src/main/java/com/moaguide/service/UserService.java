package com.moaguide.service;

import com.moaguide.refactor.coupon.service.CouponService;
import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.user.repository.UserRepository;
import com.moaguide.dto.UserDto;
import com.moaguide.refactor.user.entity.EmailHistory;
import com.moaguide.refactor.user.repository.EmailHistoryRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailHistoryRepository emailHistoryRepository;
	private final CouponService couponService;

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
		return passwordEncoder.matches(password, user.getPassword());
	}

	public void updatePassword(String nickname, String changePassword) {
		if (nickname.endsWith(".com")) {
			userRepository.updatePasswordbyEmail(nickname, passwordEncoder.encode(changePassword));
		} else {
			userRepository.updatePasswordbyNickname(nickname,
				passwordEncoder.encode(changePassword));
		}
	}

	public String delete(String nickname) {
		try {
			userRepository.delete(userRepository.findUserByNickName(nickname));
			return "회원 탈퇴 성공";
		} catch (Exception e) {
			return "회원 탈퇴 실패";
		}
	}

	public boolean findDuplication(String email) {
		Optional<User> duplication = userRepository.findByEmailAndLoginType(email, "Local");
		return duplication.isPresent();
	}

	public String updateMarketing(String nickname, int status) {
		try {
			userRepository.updateMarketting(nickname, status);
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	public Boolean checkPasswordByAdmin(String email, String password) {
		User user = userRepository.findUserByEmailAndAdmin(email);
		return passwordEncoder.matches(password, user.getPassword());
	}

	public User findByEmail(String email) {
		return userRepository.findByEmailAndLoginType(email, "admin").orElse(null);
	}

	public void emailHistory(String email) {
		EmailHistory history = emailHistoryRepository.findById(email).orElse(null);
		if (history == null) {
			emailHistoryRepository.save(new EmailHistory(email));
			couponService.firstCreate(email);
		} else {
		}
	}

	public void deleteEmailHistory(String email) {
		EmailHistory history = emailHistoryRepository.findById(email).orElse(null);
		emailHistoryRepository.delete(history);
	}
}
