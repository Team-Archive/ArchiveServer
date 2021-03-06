package site.archive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.archive.security.general.BodyCredentialAuthenticationFilter;
import site.archive.security.general.UserNamePasswordAuthenticationProvider;
import site.archive.security.oauth.OAuthUserService;
import site.archive.security.result.LoginFailureHandler;
import site.archive.security.result.LoginSuccessHandler;
import site.archive.security.token.HttpAuthTokenSupport;
import site.archive.security.token.TokenProvider;
import site.archive.security.token.jwt.JwtTokenPersistFilter;

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

        // TODO: for using local h2 db -> 추후 로컬/상용 환경 나눠 적용할
        http
            .headers().frameOptions().sameOrigin()
            .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
            .and().csrf().disable();

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
            .successHandler(successHandler)
            .failureHandler(failureHandler);
        http
            .logout()
            .disable();
        BodyCredentialAuthenticationFilter bodyCredentialAuthenticationFilter =
            bodyCredentialAuthenticationFilter(authenticationManagerBean(),
                                               mapper);
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
        BodyCredentialAuthenticationFilter filter = new BodyCredentialAuthenticationFilter("/api/v1/auth/login", manager, mapper);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

}
