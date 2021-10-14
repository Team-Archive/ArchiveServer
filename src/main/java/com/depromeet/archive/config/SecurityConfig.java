package com.depromeet.archive.config;

import com.depromeet.archive.security.login.JwtTokenPersistFilter;
import com.depromeet.archive.security.login.LoginSuccessHandler;
import com.depromeet.archive.security.login.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler successHandler;
    private final OAuthUserService userService;
    private final UserNamePasswordAuthenticationProvider provider;
    private final JwtTokenPersistFilter tokenPersistFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginProcessingUrl("/auth/login")
                .successHandler(successHandler);
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

        http.addFilterBefore(tokenPersistFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

}
