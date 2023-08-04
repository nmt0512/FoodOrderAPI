package com.nmt.FoodOrderAPI.config.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationMs}")
    private Integer jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getTokenUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

