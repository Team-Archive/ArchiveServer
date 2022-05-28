package site.archive.infra.cloud.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import site.archive.infra.cloud.aws.config.AwsS3Property;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

class S3ServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    AmazonS3 amazonS3;
    private S3Service s3Service;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        var s3Property = objectMapper.readValue("{\"region\":\"region\", \"bucketName\":\"bucketName\"}",
                                                AwsS3Property.class);
        s3Service = new S3Service(amazonS3, s3Property);
    }

    @Test
    void verifyImageFileTest() {
        // given
        var imageFile = new MockMultipartFile("imageFile", "imageFile.png", IMAGE_PNG_VALUE, new byte[0]);
        var textFile = new MockMultipartFile("textFile", "textFile.txt", TEXT_PLAIN_VALUE, new byte[0]);

        // when & then
        assertDoesNotThrow(() -> s3Service.verifyImageFile(imageFile));
        assertThatThrownBy(() -> s3Service.verifyImageFile(textFile))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void uploadTest() {

    }
}