package com.fitnesapp.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.Key;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final String SECRET_KEY;

    public JwtUtil(Environment env) {
        String secret = env.getProperty("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            logger.error(" ERROR: JWT_SECRET can not find! Please check .env file .");
            throw new IllegalStateException("HATA: JWT_SECRET can not find!");
        }

        this.SECRET_KEY = secret;
        logger.info("JWT YUKLENDI.");
    }

    public String extractUserId(String token) {
        try {
            Claims claims = getClaims(token);
            logger.info(" TOKEN VALIDATON DONE. Claims: {}", claims);
    
            String userId = claims.getSubject();
            if (userId == null) {
                userId = claims.get("id", String.class); 
            }
    
            logger.info("USER ID: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error(" TOKEN VALIDATION ERROR: {}", e.getMessage(), e);
            return null;
        }
    }
    
    

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
    
            if (expiration == null) {
                logger.error(" TOKEN ERROR: There is no 'exp' field in token" );
                return false;
            }
    
            boolean isValid = expiration.after(new Date());
    
            logger.info(" Is token valid? {}, Expiration: {}", isValid, expiration);
            return isValid;
        } catch (Exception e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return false;
        }
    }
    

    private Claims getClaims(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            logger.info("Token Claims: {}", claims);
            return claims;
        } catch (Exception e) {
            logger.error("JWT validation error: {}", e.getMessage(), e);
            throw new RuntimeException("Error JWT Token. Hata: " + e.getMessage());
        }
    }
    
}
