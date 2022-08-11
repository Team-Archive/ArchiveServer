package site.archive.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.archive.web.api.converter.StringToSortTypeConverter;
import site.archive.web.api.resolver.ArchivePageableArgumentResolver;
import site.archive.web.api.resolver.UserArgumentResolver;

import java.util.List;

@EnableWebMvc
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortTypeConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
        resolvers.add(new ArchivePageableArgumentResolver());
    }

}
