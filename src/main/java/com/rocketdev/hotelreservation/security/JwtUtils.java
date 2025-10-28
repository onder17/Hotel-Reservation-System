package com.rocketdev.hotelreservation.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Component
public class JwtUtils {
    private final SecretKey SECRET;
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 15; // 15 min
    private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30; // 30 days

    // TODO: Fetch secret from environment variable
    // @Value("${jwt.secret}")
    String secretString = "843567893696976453275974432697R634967R738467R678T3486576834R8763T4783876764538745673865";

    public JwtUtils() {
        // Decode the Base64-encoded secret string into its original binary format (byte
        // array)
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);

        // Create a SecretKey object from the byte array using the HMAC-SHA512 algorithm
        // "SecretKeySpec" Allows you to create a SecretKey from a raw byte array,
        // specifying the algorithm you want to use.
        this.SECRET = new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Identifies the user
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME)) // Expiration time
                .signWith(SECRET, SIG.HS512) // Signs with the secret key
                .compact(); // Generates the token as a string
    }

    public String generateRefreshToken(UserDetails userDetails, String jti, String deviceId) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Identifies the user
                .id(jti) // Unique ID for the token
                .claim("deviceId", deviceId)
                .issuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME)) // Expiration time
                .signWith(SECRET, SIG.HS512) // Signs with the secret key
                .compact(); // Generates the token as a string
    }

    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }

    public String generateJti() {
        return UUID.randomUUID().toString();
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractDeviceId(String token) {
        return extractClaims(token, claims -> claims.get("deviceId", String.class));
    }

    public String extractJti(String token) {
        return extractClaims(token, Claims::getId);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String email = extractEmail(token);
            return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("jwt hata");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
