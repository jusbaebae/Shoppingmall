package com.example.shoppingmall.global.security.jwt;

import com.example.shoppingmall.member.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;

        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes()
        );
    }

    public String createToken(Long memberId, Role role){
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ jwtProperties.getExpiration()))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch(Exception e){
            return false;
        }
    }

    private Claims getClaims(String token){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public long getMemberId(String token){
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String getRole(String token){
        return getClaims(token).get("role", String.class);
    }

    public Date getExpiration(String token){
        return getClaims(token).getExpiration();
    }
}
