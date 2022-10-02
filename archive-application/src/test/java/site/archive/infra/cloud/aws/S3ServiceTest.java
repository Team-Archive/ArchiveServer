package site.archive.infra.cloud.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import site.archive.infra.cloud.aws.config.AwsS3Property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

class S3ServiceTest {

    private static final String TEST_REGION = "REGION";
    private static final String TEST_BUCKETNAME = "BUCKET_NAME";
    private static final String TEST_CDN_ADDRESS = "CDN_ADDRESS/";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private AmazonS3 amazonS3;
    private S3Service s3Service;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        var s3Property = objectMapper.readValue("""
                                                {
                                                    "region": "%s",
                                                    "bucketName": "%s",
                                                    "cdnAddressName": "%s"
                                                }
                                                """.formatted(TEST_REGION, TEST_BUCKETNAME, TEST_CDN_ADDRESS),
                                                AwsS3Property.class);
        amazonS3 = mock(AmazonS3.class);
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

    @DisplayName("image file upload에 성공하면 CDN address로 시작하고, originalFileName으로 끝나는 이미지 주소를 반환한다")
    @Test
    void uploadSuccessTest() {
        // given
        var originalFileName = "imageFile.png";
        var directory = "directory/";
        var imageFile = new MockMultipartFile("imageFile", originalFileName, IMAGE_PNG_VALUE, new byte[0]);

        given(amazonS3.putObject(any(), any(), any(), any())).willReturn(null);

        // when
        var uploadImageUri = s3Service.upload(directory, imageFile);

        // then
        assertThat(uploadImageUri).startsWith(TEST_CDN_ADDRESS)
                                  .endsWith(originalFileName);
    }


    @DisplayName("Upload 시에 문제가 발생해 AmazonServiceException 예외가 발생하면 IllegalStateException 예외가 던져진다")
    @Test
    void uploadFailureTest() {
        // given
        var originalFileName = "imageFile.png";
        var directory = "directory/";
        var imageFile = new MockMultipartFile("imageFile", originalFileName, IMAGE_PNG_VALUE, new byte[0]);

        given(amazonS3.putObject(any(), any(), any(), any())).willThrow(AmazonServiceException.class);

        // when & then
        assertThatThrownBy(() -> s3Service.upload(directory, imageFile))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageStartingWith("Failed to upload the file");
    }

    @DisplayName("image file remove에 성공하면 예외가 발생하지 않는다")
    @Test
    void removeSuccessTest() {
        // given
        var originalFileName = "imageFile.png";
        var originalFileUri = "https://test.com/%s".formatted(originalFileName);


        doNothing()
            .when(amazonS3).deleteObject(TEST_BUCKETNAME, originalFileName);

        // when
        s3Service.remove(originalFileUri);

        // then
        verify(amazonS3).deleteObject(TEST_BUCKETNAME, originalFileName);
    }


    @DisplayName("Remove 시에 문제가 발생해 AmazonServiceException 예외가 발생하면 IllegalStateException 예외가 던져진다")
    @Test
    void removeFailureTest() {
        // given
        var originalFileName = "some-of-the-imageFile.png";
        var originalFileUri = "https://test.com/%s".formatted(originalFileName);

        doThrow(AmazonServiceException.class)
            .when(amazonS3).deleteObject(TEST_BUCKETNAME, originalFileName);

        // when & then
        assertThatThrownBy(() -> s3Service.remove(originalFileUri))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageStartingWith("Failed to remove the file");
    }

}