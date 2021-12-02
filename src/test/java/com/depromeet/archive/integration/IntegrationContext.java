package com.depromeet.archive.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationContext {
    @Bean
    public ApiHelper helper() {
        return new ApiHelper();
    }
}
