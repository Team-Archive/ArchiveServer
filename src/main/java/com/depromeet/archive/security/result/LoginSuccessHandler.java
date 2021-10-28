package com.depromeet.archive.security.result;


import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import com.depromeet.archive.security.common.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider provider;
    private final HttpAuthTokenSupport tokenSupport;

    public LoginSuccessHandler(TokenProvider provider, HttpAuthTokenSupport tokenSupport) {
        this.provider = provider;
        this.tokenSupport = tokenSupport;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserInfo authToken = principal.getUserInfo();
        tokenSupport.injectToken(httpServletResponse, provider.createToken(authToken));
    }


}
