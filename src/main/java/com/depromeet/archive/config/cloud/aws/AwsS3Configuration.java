package com.depromeet.archive.config.cloud.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = AwsS3Property.class)
public class AwsS3Configuration {

    @Bean
    public AmazonS3 s3(AwsCredentialsProperty credentialsProperty, AwsS3Property s3Property) {
        var awsCredentials = new BasicAWSCredentials(credentialsProperty.getAccessKey(), credentialsProperty.getSecretKey());
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(s3Property.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
