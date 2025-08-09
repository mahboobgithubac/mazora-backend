package com.mazoraapp.service.impl;

import com.mazoraapp.dto.AuthRequest;
import com.mazoraapp.dto.AuthResponse;
import com.mazoraapp.model.User;
import com.mazoraapp.repository.UserRepository;
import com.mazoraapp.config.JwtService;
import com.mazoraapp.service.AuthService;
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

//	@Override
//	public com.mazoraapp.service.AuthResponse login(com.mazoraapp.service.AuthRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public com.mazoraapp.service.AuthResponse register(com.mazoraapp.service.User user) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}