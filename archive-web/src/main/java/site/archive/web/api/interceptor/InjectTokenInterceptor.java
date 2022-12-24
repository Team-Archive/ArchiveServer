package site.archive.web.api.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;
import site.archive.web.config.security.token.jwt.JwtAuthenticationToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class InjectTokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        if (SecurityContextHolder.getContext().getAuthentication()
                instanceof JwtAuthenticationToken authentication) {
            var token = tokenProvider.createToken(authentication.getUserInfo());
            tokenSupport.injectToken(response, token);
        }
    }

}
