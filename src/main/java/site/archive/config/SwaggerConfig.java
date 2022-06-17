package site.archive.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.archive.domain.user.info.UserInfo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Configuration
public class SwaggerConfig implements WebMvcOpenApiTransformationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${archive.server.url}")
    private String baseUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                   .useDefaultResponseMessages(false)
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("site.archive.api"))
                   .paths(PathSelectors.any())
                   .build()
                   .apiInfo(apiInfo())
                   .securityContexts(List.of(securityContext()))
                   .securitySchemes(List.of(apiKey()))
                   .ignoredParameterTypes(UserInfo.class);
    }

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification();
        Server server = new Server();
        server.setDescription("Archive server");
        server.setUrl(baseUrl);
        openApi.setServers(List.of(server));
        return openApi;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                   .title("Archive Swagger")
                   .version("2.0")
                   .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                              .securityReferences(defaultAuth())
                              .build();
    }

    private List<SecurityReference> defaultAuth() {
        var authorizationScope = new AuthorizationScope("global", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
    }

}