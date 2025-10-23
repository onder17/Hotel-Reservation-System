package com.rocketdev.hotelreservation.business.concretes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rocketdev.hotelreservation.business.abstracts.AuthService;
import com.rocketdev.hotelreservation.business.requests.LoginRequest;
import com.rocketdev.hotelreservation.business.requests.RegisterRequest;
import com.rocketdev.hotelreservation.core.utilities.exceptions.BusinessException;
import com.rocketdev.hotelreservation.dataAccess.abstracts.UserRepository;
import com.rocketdev.hotelreservation.entities.concretes.Role;
import com.rocketdev.hotelreservation.entities.concretes.User;
import com.rocketdev.hotelreservation.security.JwtUtils;

@Service
public class AuthManager implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException("Email already exists!");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fname(registerRequest.getFname())
                .lname(registerRequest.getLname())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return new ResponseEntity<String>("Register Successful", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));
        var user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        var token = jwtUtils.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
