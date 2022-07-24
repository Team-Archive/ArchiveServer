package site.archive.infra.cloud.aws.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.s3")
@Getter
@Setter
public class AwsS3Property {

    @Value("${cloud.aws.region.static}")
    private String region;

    private String bucketName;
    private String cdnAddressName;

}
