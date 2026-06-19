package com.ngon.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService
{
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "superduperduperverysupersecretsupersecretkey".getBytes()
    );

    public String generateToken(String username)
    {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenExpired(String token)
    {
        Date exp = getClaims(token).getExpiration();
        return exp.before(new Date());
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails)
    {
        String username = getClaims(token).getSubject();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Claims getClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
