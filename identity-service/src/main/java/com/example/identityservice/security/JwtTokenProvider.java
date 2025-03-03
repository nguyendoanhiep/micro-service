package com.example.identityservice.security;

import com.example.identityservice.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    public SecretKey getSigningKey() {
        String secretCode = "Hieppnguyenn199925312345Hieppnguyenn199925312345Hieppnguyenn199925312345";
        byte[] keyBytes = Decoders.BASE64.decode(secretCode);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 604800000L))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username", String.class);
    }
    public Long getIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}