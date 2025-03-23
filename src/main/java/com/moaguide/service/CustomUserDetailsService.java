package com.moaguide.service;

import com.moaguide.refactor.user.entity.User;
import com.moaguide.refactor.user.repository.UserRepository;
import com.moaguide.dto.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User userData = userRepository.findByEmailAndLoginType(username, "local")
			.orElseThrow(
				() -> new UsernameNotFoundException("User not found with email: " + username));

		// UserDetails에 담아서 return하면 AuthenticationManager가 검증함
		return new CustomUserDetails(userData);
	}
}
