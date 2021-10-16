package com.depromeet.archive.security.token.jwt;


import com.depromeet.archive.domain.user.entity.UserRole;
import com.depromeet.archive.security.token.TokenProvider;
import com.depromeet.archive.security.result.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class JwtTokenProvider implements TokenProvider {

    private final String SECRET_KEY;

    public JwtTokenProvider(String key) {
        SECRET_KEY = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String createToken(AuthToken info) {
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 60 * 60 * 2);
        return Jwts
                    .builder()
                    .setSubject("user")
                    .claim("id", info)
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "ES56")
                    .setHeaderParam("kid", "default")
                    .setExpiration(date)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                    .compact();
    }

    public AuthToken parseUserInfoFromToken(String token) {
        Claims jwtClaims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
        HashMap<String, String> map = (HashMap<String, String>) jwtClaims.get("id");
        return AuthToken
                .builder()
                .userId(Long.parseLong(map.get("userId")))
                .userRole(UserRole.fromRoleString(map.get("userRole")))
                .mailAddress(map.get("mailAddress"))
                .userName(map.get("userName"))
                .build();
    }
}
