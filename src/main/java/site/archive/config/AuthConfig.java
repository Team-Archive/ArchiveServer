package site.archive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.user.UserAuthService;
import site.archive.domain.user.UserRegisterService;
import site.archive.domain.user.UserService;
import site.archive.security.authz.ArchiveAdminOrAuthorChecker;
import site.archive.security.general.UserNamePasswordAuthenticationProvider;
import site.archive.security.oauth.OAuthUserService;
import site.archive.security.result.LoginFailureHandler;
import site.archive.security.result.LoginSuccessHandler;
import site.archive.security.token.HttpAuthTokenSupport;
import site.archive.security.token.TokenProvider;
import site.archive.security.token.jwt.JwtTokenProvider;
import site.archive.security.token.jwt.JwtTokenSupport;


@Configuration
public class AuthConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public UserNamePasswordAuthenticationProvider directLoginProvider(UserAuthService userAuthService) {
        return new UserNamePasswordAuthenticationProvider(userAuthService);
    }

    @Bean
    public OAuthUserService oAuthUserService(UserRegisterService userRegisterService) {
        return new OAuthUserService(userRegisterService);
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
                                              UserService userService) {
        return new LoginSuccessHandler(tokenProvider, authTokenSupport, userService);
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
    public ArchiveAdminOrAuthorChecker checker(ArchiveRepository repository) {
        return new ArchiveAdminOrAuthorChecker(repository);
    }
}
