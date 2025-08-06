package com.mazora.backend.service.impl;

import com.mazora.backend.dto.AuthRequest;
import com.mazora.backend.dto.AuthResponse;
import com.mazora.backend.model.User;
import com.mazora.backend.repository.UserRepository;
import com.mazora.backend.config.JwtService;
import com.mazora.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtService jwtService;
	@Autowired
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public AuthResponse login(AuthRequest request) {
		System.out.println("Inside login method");
		User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		System.out.println("User in side login method" + user);
		if (!encoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String token = jwtService.generateToken(user.getEmail());
		return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
	}

	@Override
	public AuthResponse register(User user) {
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already registered");
		}

		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		String token = jwtService.generateToken(user.getEmail());
		// 3. Prepare response

		return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
	}
}