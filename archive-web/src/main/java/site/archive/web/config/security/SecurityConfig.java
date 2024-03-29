package site.archive.web.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.archive.web.config.security.authn.AdminLoginAuthenticationFilter;
import site.archive.web.config.security.authn.CustomAuthenticationEntryPoint;
import site.archive.web.config.security.authn.LoginAuthenticationFilter;
import site.archive.web.config.security.authn.UserNamePasswordAuthenticationProvider;
import site.archive.web.config.security.authz.CustomAccessDeniedHandler;
import site.archive.web.config.security.common.handler.LoginFailureHandler;
import site.archive.web.config.security.common.handler.LoginSuccessHandler;
import site.archive.web.config.security.oauth.OAuthUserServiceV1;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;
import site.archive.web.config.security.token.jwt.JwtTokenPersistFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler successHandler;
    private final LoginFailureHandler failureHandler;
    private final OAuthUserServiceV1 oAuthUserServiceV1;
    private final UserNamePasswordAuthenticationProvider provider;
    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;
    private final ObjectMapper mapper;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager)
        throws Exception {
        // @formatter:off
        return http.cors().configurationSource(corsConfigurationSource()).and()
                   .csrf().disable()
                   .formLogin().disable()
                   .httpBasic().disable()
                   .logout().disable()
                   .headers().frameOptions().sameOrigin().and()
                   .authorizeHttpRequests(auth -> auth
                      .requestMatchers("/actuator/**").permitAll()
                      .requestMatchers("/api/v1/**").permitAll()
                      .requestMatchers("/api/v2/user/duplicate/**").permitAll()
                      .requestMatchers("/api/v2/auth/register/**").permitAll()
                      .requestMatchers("/api/v2/auth/login/social").permitAll()
                      .requestMatchers("/login/**").permitAll()
                      .requestMatchers(HttpMethod.GET, "/exception/**").permitAll()
                      .anyRequest().authenticated())
                   .exceptionHandling()
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler).and()
                   .addFilterBefore(loginAuthenticationFilter(authenticationManager, mapper),
                                    UsernamePasswordAuthenticationFilter.class)
                   .addFilterBefore(adminLoginAuthenticationFilter(authenticationManager, mapper),
                                    UsernamePasswordAuthenticationFilter.class)
                   .addFilterBefore(tokenPersistFilter(),
                                    UsernamePasswordAuthenticationFilter.class)
                   // TODO: OAuth는 따로 지원 중. 추후 안드로이드는 Security에서 지원해야할 수 있으니 주석처리.
                   // 적용 시, Access denied (JWT Filter)에서 redirection이 일어나지 않도록 확인이 필요.
                   // .oauth2Login().userInfoEndpoint().userService(userService).and()
                   // .successHandler(successHandler).failureHandler(failureHandler).and()
                   .build();
        // @formatter:on
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**",
                                                     "/favicon.ico",
                                                     "/error",
                                                     "/swagger-ui/**",
                                                     "/swagger-resources/**",
                                                     "/v3/api-docs/**");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // TODO: 정적 페이지 주소로 설정 예정
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));
        configuration.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/exception/**", configuration);
        return source;
    }

    private LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager manager, ObjectMapper mapper) {
        var filter = new LoginAuthenticationFilter("/api/v1/auth/login", manager, mapper);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    private AdminLoginAuthenticationFilter adminLoginAuthenticationFilter(AuthenticationManager manager, ObjectMapper mapper) {
        var filter = new AdminLoginAuthenticationFilter("/api/v1/auth/login/admin", manager, mapper);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    private JwtTokenPersistFilter tokenPersistFilter() {
        return new JwtTokenPersistFilter(tokenSupport, tokenProvider);
    }

}
