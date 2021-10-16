package com.depromeet.archive.config;

import com.depromeet.archive.security.general.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.result.LoginSuccessHandler;
import com.depromeet.archive.security.token.jwt.JwtTokenPersistFilter;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import com.depromeet.archive.security.oauth.UserPrincipalConverter;
import com.depromeet.archive.security.oauth.converter.KakaoPrincipalConverter;
import com.depromeet.archive.security.token.jwt.JwtTokenProvider;
import com.depromeet.archive.security.token.jwt.JwtTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public UserNamePasswordAuthenticationProvider directLoginProvider() {
        return new UserNamePasswordAuthenticationProvider(userService);
    }

    @Bean
    public OAuthUserService oAuthUserService(List<UserPrincipalConverter> converterList)  {
        OAuthUserService oAuthUserService =  new OAuthUserService(userService);
        for (UserPrincipalConverter converter : converterList)
            oAuthUserService.addPrincipalConverter(converter);
        return oAuthUserService;
    }

    @Bean
    public UserPrincipalConverter kakaoConverter() {
        return new KakaoPrincipalConverter();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new JwtTokenProvider(secretKey);
    }

    @Bean
    public HttpAuthTokenSupport tokenSupport() {
        return new JwtTokenSupport();
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler(tokenProvider(), tokenSupport());
    }

    @Bean
    public JwtTokenPersistFilter tokenPersistFilter() {
        return new JwtTokenPersistFilter(tokenSupport(), tokenProvider());
    }
}
