package site.archive.security.token.jwt;


import site.archive.domain.user.info.UserInfo;
import site.archive.exception.security.TokenNotFoundException;
import site.archive.security.token.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class JwtTokenProvider implements TokenProvider {

    private final String secretKey;
    private final ObjectMapper mapper;

    public JwtTokenProvider(String key, ObjectMapper mapper) {
        secretKey = Base64.getEncoder().encodeToString(key.getBytes());
        this.mapper = mapper;
    }

    public String createToken(UserInfo info) {
        Date date = new Date();
        date.setTime(date.getTime() + 1000L * 60 * 60 * 24 * 30);
        return Jwts
            .builder()
            .setSubject("user")
            .claim("info", info)
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "ES56")
            .setHeaderParam("kid", "default")
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .compact();
    }

    public UserInfo parseUserInfoFromToken(String token) {
        try {
            Claims jwtClaims = Jwts.parser()
                                   .setSigningKey(secretKey.getBytes())
                                   .parseClaimsJws(token)
                                   .getBody();
            HashMap<String, Object> map = (HashMap<String, Object>) jwtClaims.get("info");
            UserInfo info = mapper.convertValue(map, UserInfo.class);
            log.debug("토큰 파싱 결과; id: {}, email: {}, role: {}", info.getUserId(), info.getMailAddress(), info.getUserRole());
            return info;
        } catch (Exception e) {
            log.warn("토큰 파싱 실패!", e);
            throw new TokenNotFoundException();
        }
    }

}
