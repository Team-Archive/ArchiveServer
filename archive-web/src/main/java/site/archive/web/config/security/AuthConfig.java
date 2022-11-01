package site.archive.web.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.archive.service.archive.ArchiveService;
import site.archive.service.user.UserAuthService;
import site.archive.service.user.UserRegisterServiceV1;
import site.archive.web.config.security.authn.UserNamePasswordAuthenticationProvider;
import site.archive.web.config.security.authz.ArchiveAdminOrAuthorChecker;
import site.archive.web.config.security.common.handler.LoginFailureHandler;
import site.archive.web.config.security.common.handler.LoginSuccessHandler;
import site.archive.web.config.security.oauth.OAuthUserServiceV1;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;
import site.archive.web.config.security.token.jwt.JwtTokenProvider;
import site.archive.web.config.security.token.jwt.JwtTokenSupport;


@Configuration
public class AuthConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public UserNamePasswordAuthenticationProvider directLoginProvider(UserAuthService userAuthService) {
        return new UserNamePasswordAuthenticationProvider(userAuthService);
    }

    @Bean
    public OAuthUserServiceV1 oAuthUserServiceV1(UserRegisterServiceV1 userRegisterServiceV1) {
        return new OAuthUserServiceV1(userRegisterServiceV1);
    }

    @Bean
    public TokenProvider tokenProvider(ObjectMapper mapper) {
        return new JwtTokenProvider(secretKey, mapper);
    }

    @Bean
    public HttpAuthTokenSupport tokenSupport() {
        return new JwtTokenSupport();
    }

    @Bean
    public LoginSuccessHandler successHandler(TokenProvider tokenProvider,
                                              HttpAuthTokenSupport authTokenSupport,
                                              UserAuthService userAuthService) {
        return new LoginSuccessHandler(tokenProvider, authTokenSupport, userAuthService);
    }

    @Bean
    public LoginFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ArchiveAdminOrAuthorChecker checker(ArchiveService archiveService) {
        return new ArchiveAdminOrAuthorChecker(archiveService);
    }

}
