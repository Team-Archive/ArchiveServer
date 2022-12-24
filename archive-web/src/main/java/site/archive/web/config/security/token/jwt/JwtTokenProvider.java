package site.archive.web.config.security.token.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import site.archive.common.exception.security.TokenNotFoundException;
import site.archive.domain.user.UserInfo;
import site.archive.web.config.security.token.TokenProvider;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtTokenProvider implements TokenProvider {

    private static final long DAY_30 = 1000L * 60 * 60 * 24 * 30;
    private static final String CLAIM_INFO_KEY = "info";

    private final Key secretKey;
    private final ObjectMapper mapper;

    public JwtTokenProvider(String key, ObjectMapper mapper) {
        var encodedKey = Base64.getEncoder().encodeToString(key.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
        this.mapper = mapper;
    }

    public String createToken(UserInfo info) {
        return Jwts
                   .builder()
                   .setSubject("user")
                   .claim(CLAIM_INFO_KEY, info)
                   .setHeaderParam("typ", "JWT")
                   .setHeaderParam("alg", "ES56")
                   .setHeaderParam("kid", "default")
                   .setExpiration(getTokenExpiredDate())
                   .signWith(secretKey)
                   .compact();
    }

    private Date getTokenExpiredDate() {
        Date date = new Date();
        date.setTime(date.getTime() + DAY_30);
        return date;
    }

    public UserInfo parseUserInfoFromToken(String token) {
        var jwtParser = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build();
        var tokenClaim = jwtParser.parseClaimsJws(token).getBody();
        try {
            var userInfo = mapper.convertValue(tokenClaim.get(CLAIM_INFO_KEY), UserInfo.class);
            log.debug("토큰 파싱 결과; id: {}, email: {}, role: {}", userInfo.getUserId(), userInfo.getMailAddress(), userInfo.getUserRole());
            return userInfo;
        } catch (Exception e) {
            throw new TokenNotFoundException();
        }
    }

}
