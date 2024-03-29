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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

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

    @DisplayName("image file remove에 성공하면 예외가 발생하지 않는다")
    @Test
    void removeSuccessSeparatorCodeReplaceTest() {
        // given
        var originalFileName = "images/imageFile.png";
        var codeAddedFileName = "images%2FimageFile.png";
        var originalFileUri = "https://test.com/%s".formatted(codeAddedFileName);

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


    @DisplayName("Image update 시, outdatedFileUri가 없으면 remove가 호출되지 않고, upload만 호출된다")
    @Test
    void updateOnlyUploadTest() {
        // given
        var originalFileName = "imageFile.png";
        var directory = "directory/";
        var imageFile = new MockMultipartFile("imageFile", originalFileName, IMAGE_PNG_VALUE, new byte[0]);

        given(amazonS3.putObject(any(), any(), any(), any())).willReturn(null);


        // when
        var updateFileUri = s3Service.update(directory, null, imageFile);

        // then
        assertThat(updateFileUri).startsWith(TEST_CDN_ADDRESS)
                                 .endsWith(originalFileName);
        verify(amazonS3, never()).deleteObject(any(), any());
    }


    @DisplayName("Image update 시, outdatedFileUri가 있으면 remove, upload가 호출된다")
    @Test
    void updateUploadRemoveTest() {
        // given
        var originalFileName = "imageFile.png";
        var directory = "directory/";
        var imageFile = new MockMultipartFile("imageFile", originalFileName, IMAGE_PNG_VALUE, new byte[0]);
        var originalFileUri = "https://test.com/%s".formatted(originalFileName);

        given(amazonS3.putObject(any(), any(), any(), any())).willReturn(null);
        doNothing()
            .when(amazonS3).deleteObject(TEST_BUCKETNAME, originalFileName);

        // when
        var updateFileUri = s3Service.update(directory, originalFileUri, imageFile);

        // then
        assertThat(updateFileUri).startsWith(TEST_CDN_ADDRESS)
                                 .endsWith(originalFileName);
        verify(amazonS3).deleteObject(TEST_BUCKETNAME, originalFileName);
    }


}