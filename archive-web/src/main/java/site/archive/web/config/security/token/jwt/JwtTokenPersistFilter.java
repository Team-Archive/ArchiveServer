package site.archive.web.config.security.token.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenPersistFilter extends OncePerRequestFilter {

    private static final String MONITORING_PATH_PREFIX = "/actuator";

    private final HttpAuthTokenSupport httpTokenExtractor;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(MONITORING_PATH_PREFIX)) {
            try {
                var tokenStr = httpTokenExtractor.extractToken(request);
                var authToken = tokenProvider.parseUserInfoFromToken(tokenStr);
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(authToken));
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

}
