package com.depromeet.archive.config;

import com.depromeet.archive.security.token.jwt.JwtTokenPersistFilter;
import com.depromeet.archive.security.result.LoginSuccessHandler;
import com.depromeet.archive.security.general.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private OAuthUserService userService;
    @Autowired
    private UserNamePasswordAuthenticationProvider provider;
    @Autowired
    private JwtTokenPersistFilter tokenPersistFilter;

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
