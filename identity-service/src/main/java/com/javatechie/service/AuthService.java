package com.javatechie.service;

import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	public String saveUser(UserCredential credential) {
		Optional<UserCredential> optionalUser = repository.findByName(credential.getName());

		if (optionalUser.isPresent()) {
			return "user already exists in the system";
		}

		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		repository.save(credential);
		return "user added to the system";
	}

	public String generateToken(Authentication authentication) {
		return jwtService.generateToken(authentication);
	}

	public void validateToken(String token, String role) {
		
		if (Objects.nonNull(role)) {
			String userRole = jwtService.getRolesFromJwtToken(token);
			
			if (userRole.equals(role)) {
				System.out.println("user has role");
			}
		}
		
		jwtService.validateToken(token);
	}

}
