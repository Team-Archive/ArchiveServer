package site.archive.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.archive.web.api.converter.StringToSortTypeConverter;
import site.archive.web.api.interceptor.InjectTokenInterceptor;
import site.archive.web.api.resolver.ArchivePageableArgumentResolver;
import site.archive.web.api.resolver.UserArgumentResolver;
import site.archive.web.config.security.token.HttpAuthTokenSupport;
import site.archive.web.config.security.token.TokenProvider;

import java.util.List;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortTypeConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
        resolvers.add(new ArchivePageableArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InjectTokenInterceptor(tokenProvider, tokenSupport))
                .addPathPatterns("/api/v1/auth/**",
                                 "/api/v2/auth/register/**",
                                 "/api/v2/auth/login/social");
    }

}
