package com.synapsis.shop.util;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.synapsis.shop.ShopApplication;
import com.synapsis.shop.dbo.User;
import com.synapsis.shop.dto.UserDTO;
import com.synapsis.shop.exception.UnauthorizeException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.expired-in-minite}")
    Long expiredInMinite;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("user_email", user.getEmail());

        long expiredInMinute = Instant.now().plusSeconds(expiredInMinite * 60).toEpochMilli();

        return Jwts.builder()
                .claims(claims)
                .issuer(ShopApplication.class.getName())
                .issuedAt(new Date())
                .expiration(new Date(expiredInMinute))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))
                .compact();
    }

    public UserDTO parseToken(String authToken) throws Exception {
        try {
            String jwt = authToken.replace("Bearer ", "")
                    .replace("bearer", "");

            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            return UserDTO.builder()
                    .id(claims.get("user_id", Integer.class))
                    .email(claims.get("user_email", String.class))
                    .build();
        } catch (Exception e) {
            throw new UnauthorizeException();
        }

    }

}