package com.depromeet.archive.security.token;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpAuthTokenSupport {

    String extractToken(HttpServletRequest target);

    void injectToken(HttpServletResponse response, String token);

}
