package com.mazora.backend.service;

import com.mazora.backend.dto.AuthRequest;
import com.mazora.backend.dto.AuthResponse;
import com.mazora.backend.model.User;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(User user);
}