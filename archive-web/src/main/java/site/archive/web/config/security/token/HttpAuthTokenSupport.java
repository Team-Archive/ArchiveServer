package site.archive.web.config.security.token;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpAuthTokenSupport {

    String extractToken(HttpServletRequest target);

    void injectToken(HttpServletResponse response, String token);

}
