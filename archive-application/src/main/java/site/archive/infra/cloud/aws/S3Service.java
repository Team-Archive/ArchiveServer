package site.archive.infra.cloud.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.archive.common.exception.infra.FileInvalidException;
import site.archive.infra.cloud.aws.config.AwsS3Property;
import site.archive.service.archive.ArchiveImageService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements ArchiveImageService {

    private final AmazonS3 amazonS3;
    private final AwsS3Property s3Property;

    @Override
    public String upload(final String directory, final MultipartFile imageFile) {
        var fileName = String.format("%s/%s-%s", directory, UUID.randomUUID(), imageFile.getOriginalFilename());
        var bucket = s3Property.getBucketName();

        try {
            var objectMetadata = getObjectMetadataFromFile(imageFile);
            var inputStream = imageFile.getInputStream();
            amazonS3.putObject(bucket, fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new FileInvalidException();
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
        return s3Property.getCdnAddressName() + URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    private ObjectMetadata getObjectMetadataFromFile(final MultipartFile imageFile) {
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(imageFile.getContentType());
        objectMetadata.setContentLength(imageFile.getSize());
        return objectMetadata;
    }

}
