package org.wasim.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.wasim.userservice.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final SecretKey key =
            Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // 15 minutes access token
    private static final long ACCESS_TOKEN_EXPIRATION =
            1000 * 60 * 15;

    // 7 days refresh token
    private static final long REFRESH_TOKEN_EXPIRATION =
            1000 * 60 * 60 * 24 * 7;

    // =============================================
    // Generate Access Token
    // =============================================
    public String generateAccessToken(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiryDate)
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .signWith(key)
                .compact();
    }

    // =============================================
    // Generate Refresh Token
    // =============================================
    public String generateRefreshToken(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // =============================================
    // Extract All Claims
    // =============================================
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =============================================
    // Extract Username
    // =============================================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // =============================================
    // Extract Expiration Date
    // =============================================
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // =============================================
    // Check Token Expired
    // =============================================
    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    // =============================================
    // Validate Token
    // =============================================
    public boolean validateToken(String token, User user) {

        String username = extractUsername(token);

        return username.equals(user.getEmail())
                && !isTokenExpired(token);
    }

    // =============================================
    // Generate New Access Token Using Refresh Token
    // =============================================
    public String generateAccessTokenFromRefreshToken(String refreshToken) {

        if (isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh Token Expired");
        }

        Claims claims = extractAllClaims(refreshToken);

        String email = claims.getSubject();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
}
