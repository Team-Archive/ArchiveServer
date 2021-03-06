package site.archive.security.general;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import site.archive.domain.user.UserAuthService;
import site.archive.domain.user.command.LoginCommand;
import site.archive.domain.user.info.UserInfo;
import site.archive.exception.BaseException;
import site.archive.exception.security.WrappingAuthenticationException;
import site.archive.security.common.UserPrincipal;

import java.util.Collections;

public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthService userAuthService;

    public UserNamePasswordAuthenticationProvider(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        LoginCommand command = makeLoginCommandFormToken(passwordAuthenticationToken);
        UserInfo userInfo = tryLoginOrThrow(command);
        return getCompleteAuthToken(userInfo, command.getPassword());
    }

    private UserInfo tryLoginOrThrow(LoginCommand command) {
        try {
            return userAuthService.tryLoginAndReturnInfo(command);
        } catch (BaseException exception) {
            throw new WrappingAuthenticationException(exception);
        }
    }

    private LoginCommand makeLoginCommandFormToken(UsernamePasswordAuthenticationToken token) {
        String userName = token.getName();
        String password = (String) token.getCredentials();
        return new LoginCommand(userName, password);
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
