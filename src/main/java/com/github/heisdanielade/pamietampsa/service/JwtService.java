package com.github.heisdanielade.pamietampsa.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "6k7Pv9j/MXPef+zrYSt+o9mc/mTUHsNUmGe1Jb+ik+ZJpF82D5DpH9iw0OSYP1zj";

    public String extractUsername(String token) {
        return null;
    }

    private Claims extractAllClaims(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSignInKey() {
        return Decoders.BASE64.decode(SECRET_KEY);

        // Changed depend...version, parser() to parserBuilder()  &
        // Stopped here..............

    }
}
