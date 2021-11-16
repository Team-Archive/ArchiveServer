package com.depromeet.archive.config;

import com.depromeet.archive.security.general.UserNamePasswordAuthenticationProvider;
import com.depromeet.archive.security.oauth.OAuthUserService;
import com.depromeet.archive.security.result.LoginSuccessHandler;
import com.depromeet.archive.security.token.jwt.JwtTokenPersistFilter;
import lombok.RequiredArgsConstructor;
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

        // TODO: for using local h2 db -> 추후 로컬/상용 환경 나눠 적용할
        http
                .headers().frameOptions().sameOrigin()
                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().disable();

        http
                .formLogin()
                .loginProcessingUrl("/api/v1/archive/login")
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
