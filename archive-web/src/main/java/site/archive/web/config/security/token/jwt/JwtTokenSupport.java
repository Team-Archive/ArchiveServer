package site.archive.web.config.security.token.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import site.archive.common.exception.security.TokenNotFoundException;
import site.archive.web.config.security.token.HttpAuthTokenSupport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class JwtTokenSupport implements HttpAuthTokenSupport {

    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public String extractToken(HttpServletRequest target) {
        try {
            String tokenTypeAndStr = target.getHeader(HttpHeaders.AUTHORIZATION);
            log.debug("Parsing token in header: {} - Request path: {}", tokenTypeAndStr, target.getRequestURI());
            return tokenTypeAndStr.split(" ")[1];
        } catch (Exception e) {
            throw new TokenNotFoundException();
        }
    }

    @Override
    public void injectToken(HttpServletResponse dest, String token) {
        dest.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + " " + token);
    }
}
