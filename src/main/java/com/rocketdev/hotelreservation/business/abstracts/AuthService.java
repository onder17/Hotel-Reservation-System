package com.rocketdev.hotelreservation.business.abstracts;

import org.springframework.http.ResponseEntity;

import com.rocketdev.hotelreservation.business.requests.LoginRequest;
import com.rocketdev.hotelreservation.business.requests.RegisterRequest;

public interface AuthService {
    public ResponseEntity<?> register(RegisterRequest registerRequest);

    public ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> refresh(String refreshToken, String deviceId);

    ResponseEntity<?> logout(String deviceId);

    public Long getCurrentUserId();
}
