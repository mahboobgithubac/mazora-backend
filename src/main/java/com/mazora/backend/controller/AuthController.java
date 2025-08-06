package com.mazora.backend.controller;

import com.mazora.backend.dto.AuthRequest;
import com.mazora.backend.dto.AuthResponse;
import com.mazora.backend.model.User;
import com.mazora.backend.service.AuthService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = {"http://localhost:3000", "https://your-app.netlify.app"})
public class AuthController {
	 private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/testApi")
    public String TestApi() {

        return "TestApi Running";
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody User user) {
    	System.out.println("user ->"+user);
    	  logger.info("Registering new user: {}", user.getEmail());
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
    	  logger.info("Login try by  user: {}", request.getEmail());
        return authService.login(request);
    }
}