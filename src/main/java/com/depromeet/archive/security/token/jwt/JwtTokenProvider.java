package com.depromeet.archive.security.token.jwt;


import com.depromeet.archive.domain.user.entity.UserRole;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.token.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class JwtTokenProvider implements TokenProvider {

    private final String SECRET_KEY;
    private final ObjectMapper mapper;

    public JwtTokenProvider(String key, ObjectMapper mapper) {
        SECRET_KEY = Base64.getEncoder().encodeToString(key.getBytes());
        this.mapper = mapper;
    }

    public String createToken(UserInfo info) {
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

    public UserInfo parseUserInfoFromToken(String token) {
        Claims jwtClaims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
        HashMap<String, Object> map = (HashMap<String, Object>) jwtClaims.get("id");
        return mapper.convertValue(map, UserInfo.class);
    }

}
