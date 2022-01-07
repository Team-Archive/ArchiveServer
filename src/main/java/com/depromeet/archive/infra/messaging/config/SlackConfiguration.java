package com.depromeet.archive.infra.messaging.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {SlackProperty.class})
public class SlackConfiguration {
}
