package site.archive.infra.cloud.aws.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = AwsCredentialsProperty.class)
public class AwsConfiguration {
}