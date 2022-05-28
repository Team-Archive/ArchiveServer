package site.archive.security.token.jwt;

import site.archive.domain.user.info.UserInfo;
import site.archive.exception.security.TokenNotFoundException;
import site.archive.security.token.HttpAuthTokenSupport;
import site.archive.security.token.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class JwtTokenPersistFilter extends HttpFilter {

    private final HttpAuthTokenSupport httpTokenExtractor;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            String tokenStr = httpTokenExtractor.extractToken(request);
            UserInfo authToken = tokenProvider.parseUserInfoFromToken(tokenStr);
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(authToken));
            super.doFilter(request, response, chain);
        } catch (TokenNotFoundException exception) {
            super.doFilter(request, response, chain);
        } catch (Exception e) {
            log.error("Failed to set SecurityContext", e);
            super.doFilter(request, response, chain);
        }
    }

}
