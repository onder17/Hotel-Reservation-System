package com.rocketdev.hotelreservation.business.concretes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rocketdev.hotelreservation.business.abstracts.AuthService;
import com.rocketdev.hotelreservation.business.requests.LoginRequest;
import com.rocketdev.hotelreservation.business.requests.RegisterRequest;
import com.rocketdev.hotelreservation.core.utilities.exceptions.BusinessException;
import com.rocketdev.hotelreservation.dataAccess.abstracts.RefreshTokenRepository;
import com.rocketdev.hotelreservation.dataAccess.abstracts.UserRepository;
import com.rocketdev.hotelreservation.entities.concretes.RefreshToken;
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
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
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
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            // Get user details
            UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generate device ID
            String deviceId = UUID.randomUUID().toString();

            // Generate refresh token
            String jti = jwtUtils.generateJti();
            String refreshToken = jwtUtils.generateRefreshToken(user, jti, deviceId);

            // Save refresh token entity
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                    .userId(userRepository.findByEmail(loginRequest.getEmail()).get().getId())
                    .deviceId(deviceId)
                    .refreshTokenHash(jwtUtils.hashToken(refreshToken))
                    .jti(jti)
                    .expiresAt(LocalDateTime.now().plusDays(30))
                    .createdAt(LocalDateTime.now())
                    .lastUsedAt(LocalDateTime.now())
                    .build();

            refreshTokenRepository.save(refreshTokenEntity);

            // Set cookies
            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/api/auth/refresh")
                    .maxAge(30 * 24 * 60 * 60)
                    .sameSite("None")
                    .build();

            ResponseCookie deviceCookie = ResponseCookie.from("deviceId", deviceId)
                    .httpOnly(false)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(30 * 24 * 60 * 60)
                    .build();

            // Generate access token
            String accessToken = jwtUtils.generateToken(user);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, deviceCookie.toString());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(Map.of("accessToken", accessToken));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-posta veya şifre hatalı"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Sunucu hatası"));
        }
    }

    @Override
    public ResponseEntity<?> refresh(String refreshToken, String deviceId) {
        try {
            RefreshToken refreshTokenEntity = refreshTokenRepository
                    .findByRefreshTokenHash(jwtUtils.hashToken(refreshToken))
                    .orElseThrow(() -> new BusinessException("Refresh token not found"));

            if (refreshTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
                refreshTokenRepository.delete(refreshTokenEntity);
                throw new BusinessException("Refresh token expired");
            }

            UserDetails user = userDetailsService.loadUserByUsername(jwtUtils.extractEmail(refreshToken));

            String newAccessToken = jwtUtils.generateToken(user);
            String jti = jwtUtils.generateJti();
            String newRefreshToken = jwtUtils.generateRefreshToken(user, jti, deviceId);

            refreshTokenEntity.setRefreshTokenHash(jwtUtils.hashToken(newRefreshToken));
            refreshTokenEntity.setLastUsedAt(LocalDateTime.now());
            refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusDays(30));
            refreshTokenRepository.save(refreshTokenEntity);

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None") // frontend farklı domain ise None olmalı
                    .path("/api/auth/refresh")
                    .maxAge(30 * 24 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(Map.of("accessToken", newAccessToken));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Sunucu hatası"));
        }

    }

    @Override
    public ResponseEntity<?> logout(String deviceId) {
        Long userId = getCurrentUserId();
        refreshTokenRepository.findByUserIdAndDeviceId(userId, deviceId).ifPresent(refreshTokenRepository::delete);

        return ResponseEntity.ok("Logout Successful");
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            return user;
        }

        return null;
    }

    @Override
    public Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

}
