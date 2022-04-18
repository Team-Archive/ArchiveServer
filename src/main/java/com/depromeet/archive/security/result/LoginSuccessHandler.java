package com.depromeet.archive.security.result;

import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.security.common.UserPrincipal;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider provider;
    private final HttpAuthTokenSupport tokenSupport;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) {
        var principal = (UserPrincipal) authentication.getPrincipal();
        var authToken = principal.getUserInfo();
        var successToken = provider.createToken(authToken);
        log.debug("유저 로그인 성공, 아이디: {}, 이메일: {}, 토큰: {}", authToken.getUserId(), authToken.getMailAddress(), successToken);

        if (userService.isTemporaryPasswordLogin(authToken.getUserId())) {
            httpServletResponse.setStatus(HttpStatus.RESET_CONTENT.value());
        } else {
            tokenSupport.injectToken(httpServletResponse, successToken);
            httpServletResponse.setStatus(HttpStatus.OK.value());
        }
    }

}
