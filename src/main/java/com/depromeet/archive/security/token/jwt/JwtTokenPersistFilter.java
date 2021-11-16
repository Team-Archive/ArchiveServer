package com.depromeet.archive.security.token.jwt;

import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.exception.TokenNotFoundException;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenPersistFilter extends HttpFilter {

    private final HttpAuthTokenSupport httpTokenExtractor;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String tokenStr = httpTokenExtractor.extractToken(request);
            UserInfo authToken = tokenProvider.parseUserInfoFromToken(tokenStr);
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(authToken));
            super.doFilter(request, response, chain);
        } catch (TokenNotFoundException exception) {
            super.doFilter(request, response, chain);
        }
    }

}
