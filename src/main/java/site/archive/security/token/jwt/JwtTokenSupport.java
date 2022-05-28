package site.archive.security.token.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import site.archive.exception.security.TokenNotFoundException;
import site.archive.security.token.HttpAuthTokenSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtTokenSupport implements HttpAuthTokenSupport {

    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public String extractToken(HttpServletRequest target) {
        try {
            String tokenTypeAndStr = target.getHeader(HttpHeaders.AUTHORIZATION);
            log.debug("Parsing token in header: {}", tokenTypeAndStr);
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
