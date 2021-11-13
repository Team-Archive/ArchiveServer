package com.depromeet.archive.config.cloud.aws;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = AwsCredentialsProperty.class)
public class AwsConfiguration {
}