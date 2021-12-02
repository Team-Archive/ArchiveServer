package com.depromeet.archive.config;

import com.depromeet.archive.domain.archive.ArchiveRepository;
import com.depromeet.archive.domain.user.StringEncryptor;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.infra.user.StringEncryptorMock;
import com.depromeet.archive.security.authorization.permissionhandler.ArchiveAdminOrAuthorChecker;
import com.depromeet.archive.security.general.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.result.LoginFailureHandler;
import com.depromeet.archive.security.result.LoginSuccessHandler;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import com.depromeet.archive.security.oauth.UserPrincipalConverter;
import com.depromeet.archive.security.oauth.converter.KakaoPrincipalConverter;
import com.depromeet.archive.security.token.jwt.JwtTokenProvider;
import com.depromeet.archive.security.token.jwt.JwtTokenSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public UserNamePasswordAuthenticationProvider directLoginProvider(UserService userService) {
        return new UserNamePasswordAuthenticationProvider(userService);
    }

    @Bean
    public OAuthUserService oAuthUserService(UserService userService, List<UserPrincipalConverter> converterList) {
        OAuthUserService oAuthUserService = new OAuthUserService(userService);
        for (UserPrincipalConverter converter : converterList)
            oAuthUserService.addPrincipalConverter(converter);
        return oAuthUserService;
    }

    @Bean
    public UserPrincipalConverter kakaoConverter() {
        return new KakaoPrincipalConverter();
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
    public LoginSuccessHandler successHandler(TokenProvider tokenProvider, HttpAuthTokenSupport authTokenSupport) {
        return new LoginSuccessHandler(tokenProvider, authTokenSupport);
    }

    @Bean
    public LoginFailureHandler failureHandler() {return new LoginFailureHandler(); }

    @Bean
    public StringEncryptor stringEncryptorMock() {
        return new StringEncryptorMock();
    }

    @Bean
    public ArchiveAdminOrAuthorChecker checker(ArchiveRepository repository) {
        return new ArchiveAdminOrAuthorChecker(repository);
    }

}
