package com.depromeet.archive.infra.cloud.aws.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.credentials")
@Getter
@Setter
public class AwsCredentialsProperty {

    private String accessKey;
    private String secretKey;

}
