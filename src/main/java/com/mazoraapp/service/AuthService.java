package com.mazoraapp.service;

import com.mazoraapp.dto.AuthRequest;
import com.mazoraapp.dto.AuthResponse;
import com.mazoraapp.model.User;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(User user);
}