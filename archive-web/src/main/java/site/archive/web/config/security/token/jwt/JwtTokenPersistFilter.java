package site.archive.web.config.security.token.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenPersistFilter extends OncePerRequestFilter {

    private final HttpAuthTokenSupport httpTokenExtractor;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            var tokenStr = httpTokenExtractor.extractToken(request);
            var authToken = tokenProvider.parseUserInfoFromToken(tokenStr);
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(authToken));
        } catch (Exception e) {
            log.debug("JwtTokenPersistFilter error [토큰 오류]", e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

}