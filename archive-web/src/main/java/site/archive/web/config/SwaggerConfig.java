package site.archive.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${archive.server.url}")
    private String baseUrl;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                             .group("archive api")
                             .pathsToMatch("/api/**")
                             .build();
    }

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                   .info(new Info().title("Archive API")
                                   .description("Archive API 입니다")
                                   .version("2.0"))
                   .addSecurityItem(new SecurityRequirement()
                                        .addList(securitySchemeName))
                   .components(new Components()
                                   .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                                                               .name(securitySchemeName)
                                                                               .type(SecurityScheme.Type.HTTP)
                                                                               .scheme("Bearer")
                                                                               .bearerFormat("JWT")));
    }

}