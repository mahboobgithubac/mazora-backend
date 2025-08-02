package com.mazora.backend.controller;

import com.mazora.backend.dto.AuthRequest;
import com.mazora.backend.dto.AuthResponse;
import com.mazora.backend.model.User;
import com.mazora.backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/testApi")
    public String TestApi() {

        return "TestApi Running";
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody User user) {
    	System.out.println("user ->"+user);
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}