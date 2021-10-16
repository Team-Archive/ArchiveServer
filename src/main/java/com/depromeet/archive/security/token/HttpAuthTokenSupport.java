package com.depromeet.archive.security.token;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpAuthTokenSupport {
    public String extractToken(HttpServletRequest target);
    public void injectToken(HttpServletResponse response, String token);
}
