package com.rocketdev.hotelreservation.webApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketdev.hotelreservation.business.abstracts.AuthService;
import com.rocketdev.hotelreservation.business.requests.LoginRequest;
import com.rocketdev.hotelreservation.business.requests.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken, @CookieValue("deviceId") String deviceId) {
        return authService.refresh(refreshToken, deviceId);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("deviceId") String deviceId) {
        return authService.logout(deviceId);
    }

}