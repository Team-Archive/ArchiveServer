package com.depromeet.archive.security.token.jwt;

import com.depromeet.archive.exception.security.TokenNotFoundException;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtTokenSupport implements HttpAuthTokenSupport {

    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public String extractToken(HttpServletRequest target) {
        String tokenTypeAndStr = target.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("Parsing token in header: {}", tokenTypeAndStr);
        if (isInvalidToken(tokenTypeAndStr))
            throw new TokenNotFoundException();
        return tokenTypeAndStr.split(" ")[1];
    }

    private boolean isInvalidToken(String tokenTypeAndStr) {
        return tokenTypeAndStr == null || tokenTypeAndStr.isEmpty();
    }
    @Override
    public void injectToken(HttpServletResponse dest, String token) {
        dest.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + " " + token);
    }
}
