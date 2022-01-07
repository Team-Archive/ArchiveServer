package com.depromeet.archive.infra.messaging.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "slack")
@Getter
@Setter
public class SlackProperty {

    private String token;
    private String channel;

}
