package com.hahn.stock.springSecurity.service;



import com.google.common.base.Strings;
import com.hahn.stock.springSecurity.jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;

@Service
public class JwtService {


    @Getter
    public final String tokenPrefix;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtService(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.tokenPrefix = jwtConfig.getTokenPrefix();
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String generateToken(UserDetails user, String issuer) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .setExpiration(java.sql.Date.valueOf(getTokenExpirationDate()))
            .setIssuer(issuer)
            .claim("authorities", user.getAuthorities())
            .signWith(secretKey)
            .compact();
    }

    public String generateConfirmationToken(UserDetails user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("authorities", user.getAuthorities())
            .signWith(secretKey)
            .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

    }


    public LocalDate getTokenExpirationDate() {
        return LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays());
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(this.getAuthorizationHeader());
        String token = null;
        if (!Strings.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            return token;
        }
        return null;
    }
}
