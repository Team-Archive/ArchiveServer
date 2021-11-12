package com.depromeet.archive.config;

import com.depromeet.archive.controller.resolver.UserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver());
    }

    public HandlerMethodArgumentResolver userResolver() {
        return new UserArgumentResolver();
    }
}
