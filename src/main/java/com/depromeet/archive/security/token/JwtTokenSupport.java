package com.depromeet.archive.security.token;

import com.depromeet.archive.security.exception.TokenNotFoundException;
import com.depromeet.archive.security.login.HttpAuthTokenSupport;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtTokenSupport implements HttpAuthTokenSupport {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public String extractToken(HttpServletRequest target) {
        String tokenTypeAndStr = target.getHeader("Authorization");
        log.debug("Parsing token in header: " + tokenTypeAndStr);
        if (tokenTypeAndStr == null || tokenTypeAndStr.isEmpty())
            throw new TokenNotFoundException("Token header not found. Header name must be 'Authorization'");
        return tokenTypeAndStr.split(" ")[1];
    }

    @Override
    public void injectToken(HttpServletResponse dest, String token) {
        dest.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
    }
}
