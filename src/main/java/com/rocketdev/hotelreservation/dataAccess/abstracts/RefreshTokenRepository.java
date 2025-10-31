package com.rocketdev.hotelreservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketdev.hotelreservation.entities.concretes.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserIdAndDeviceId(Long userId, String deviceId);

    Optional<RefreshToken> findByRefreshTokenHash(String refreshTokenHash);

    void deleteByUserId(Long userId); // logout all devices
}
