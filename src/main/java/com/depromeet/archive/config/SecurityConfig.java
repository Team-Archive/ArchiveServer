package com.depromeet.archive.config;

import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.security.general.BodyCredentialAuthenticationFilter;
import com.depromeet.archive.security.oauth.UserPrincipalConverter;
import com.depromeet.archive.security.oauth.converter.KakaoPrincipalConverter;
import com.depromeet.archive.security.result.LoginFailureHandler;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import com.depromeet.archive.security.token.jwt.JwtTokenPersistFilter;
import com.depromeet.archive.security.result.LoginSuccessHandler;
import com.depromeet.archive.security.general.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import com.depromeet.archive.security.token.jwt.JwtTokenProvider;
import com.depromeet.archive.security.token.jwt.JwtTokenSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler successHandler;
    private final LoginFailureHandler failureHandler;
    private final OAuthUserService userService;
    private final UserNamePasswordAuthenticationProvider provider;
    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;
    private final ObjectMapper mapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable();
        http
                .httpBasic()
                .disable();
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(userService).and()
                .successHandler(successHandler);
        http
                .logout()
                .disable();
        BodyCredentialAuthenticationFilter bodyCredentialAuthenticationFilter = bodyCredentialAuthenticationFilter(authenticationManagerBean(), mapper);
        JwtTokenPersistFilter tokenPersistFilter = tokenPersistFilter();
        http.addFilterBefore(tokenPersistFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(bodyCredentialAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    public JwtTokenPersistFilter tokenPersistFilter() {
        return new JwtTokenPersistFilter(tokenSupport, tokenProvider);
    }

    public BodyCredentialAuthenticationFilter bodyCredentialAuthenticationFilter(AuthenticationManager manager, ObjectMapper mapper) {
        BodyCredentialAuthenticationFilter filter = new BodyCredentialAuthenticationFilter("/login", manager, mapper);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

}
