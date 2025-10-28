package com.rocketdev.hotelreservation.webApi.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketdev.hotelreservation.business.abstracts.AuthService;
import com.rocketdev.hotelreservation.dataAccess.abstracts.UserRepository;
import com.rocketdev.hotelreservation.entities.concretes.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<Object> response = users.stream()
                .map(u -> new Object() {
                    public final Long id = u.getId();
                    public final String email = u.getEmail();
                    public final String role = u.getRole().name();
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        User user = authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "role", user.getRole()));
    }
}
