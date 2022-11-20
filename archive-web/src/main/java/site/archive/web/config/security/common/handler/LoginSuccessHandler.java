package site.archive.web.config.security.common.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import site.archive.domain.user.UserInfo;
import site.archive.service.user.UserAuthService;
import site.archive.web.config.security.common.UserPrincipal;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider provider;
    private final HttpAuthTokenSupport tokenSupport;
    private final UserAuthService userAuthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) {
        var principal = (UserPrincipal) authentication.getPrincipal();
        var authToken = principal.getUserInfo();
        var successToken = provider.createToken(authToken);
        log.debug("유저 로그인 성공, 아이디: {}, 이메일: {}, 토큰: {}", authToken.getUserId(), authToken.getMailAddress(), successToken);

        setHttpStatusByTemporaryPasswordLogin(httpServletResponse, authToken, successToken);
    }

    // 임시 비멀번호로 로그인 한 경우, 205 반환
    private void setHttpStatusByTemporaryPasswordLogin(HttpServletResponse httpServletResponse, UserInfo authToken, String successToken) {
        tokenSupport.injectToken(httpServletResponse, successToken);
        if (userAuthService.isTemporaryPasswordLogin(authToken.getUserId())) {
            httpServletResponse.setStatus(HttpStatus.RESET_CONTENT.value());
        } else {
            httpServletResponse.setStatus(HttpStatus.OK.value());
        }
    }

}
