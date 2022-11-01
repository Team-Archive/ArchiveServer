package site.archive.web.config.security.authn;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import site.archive.common.exception.BaseException;
import site.archive.common.exception.security.WrappingAuthenticationException;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.auth.LoginCommandV1;
import site.archive.service.user.UserAuthService;
import site.archive.web.config.security.common.UserPrincipal;

import java.util.Collections;

public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthService userAuthService;

    public UserNamePasswordAuthenticationProvider(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        LoginCommandV1 command = makeLoginCommandFormToken(passwordAuthenticationToken);
        UserInfo userInfo = tryLoginOrThrow(command);
        return getCompleteAuthToken(userInfo, command.getPassword());
    }

    private UserInfo tryLoginOrThrow(LoginCommandV1 command) {
        try {
            return userAuthService.tryLoginAndReturnInfo(command);
        } catch (BaseException exception) {
            throw new WrappingAuthenticationException(exception);
        }
    }

    private LoginCommandV1 makeLoginCommandFormToken(UsernamePasswordAuthenticationToken token) {
        String userName = token.getName();
        String password = (String) token.getCredentials();
        return new LoginCommandV1(userName, password);
    }

    private UsernamePasswordAuthenticationToken getCompleteAuthToken(UserInfo loginUser, String credential) {
        UserPrincipal principal = UserPrincipal
                                      .builder()
                                      .attributes(Collections.singletonMap("password", credential))
                                      .userInfo(loginUser)
                                      .build();
        return new UsernamePasswordAuthenticationToken(principal,
                                                       credential, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
