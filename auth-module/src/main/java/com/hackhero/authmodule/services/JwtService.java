package com.hackhero.authmodule.services;

import com.hackhero.authmodule.dtos.JwtInfoDto;
import com.hackhero.domainmodule.entities.users.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "token")
@Slf4j
@Getter
@Setter
public class JwtService {

    private String signingKey;
    private Long lifetime;


    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public JwtInfoDto generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof AuthUser customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("phoneNumber", customUserDetails.getPhoneNumber());
            claims.put("role", customUserDetails.getRole());
        }

        return generateToken(claims, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String phoneNumber = extractPhoneNumber(token);
        boolean notExpired = !isTokenExpired(token);
        return phoneNumber.equals(userDetails.getUsername()) && notExpired;
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private JwtInfoDto generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return JwtInfoDto.builder()
                .token(
                        Jwts.builder()
                                .claims()
                                .add(extraClaims)
                                .subject(userDetails.getUsername())
                                .issuedAt(new Date(System.currentTimeMillis()))
                                .expiration(new Date(System.currentTimeMillis() + lifetime))
                                .and()
                                .signWith(getSigningKey(), Jwts.SIG.HS256)
                                .compact()
                )
                .createdAt(new Date(System.currentTimeMillis()))
                .expiresAt(new Date(System.currentTimeMillis() + lifetime))
                .build();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private Claims extractAllClaims(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKey));
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
