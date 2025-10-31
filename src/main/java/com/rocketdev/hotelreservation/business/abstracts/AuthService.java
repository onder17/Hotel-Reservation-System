package com.rocketdev.hotelreservation.business.abstracts;

import org.springframework.http.ResponseEntity;

import com.rocketdev.hotelreservation.business.requests.LoginRequest;
import com.rocketdev.hotelreservation.business.requests.RegisterRequest;
import com.rocketdev.hotelreservation.entities.concretes.User;

public interface AuthService {
    public ResponseEntity<?> register(RegisterRequest registerRequest);

    public ResponseEntity<?> login(LoginRequest loginRequest);

    public ResponseEntity<?> refresh(String refreshToken, String deviceId);

    public ResponseEntity<?> logout(String deviceId);

    public User getCurrentUser();

    public Long getCurrentUserId();
}
