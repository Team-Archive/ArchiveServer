package site.archive.web.config.security.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

class FileUtilsTest {

    @Test
    void verifyImageFileTest() {
        // given
        var imageFile = new MockMultipartFile("imageFile", "imageFile.png", IMAGE_PNG_VALUE, new byte[0]);
        var textFile = new MockMultipartFile("textFile", "textFile.txt", TEXT_PLAIN_VALUE, new byte[0]);

        // when & then
        assertDoesNotThrow(() -> FileUtils.verifyImageFile(imageFile));
        assertThatThrownBy(() -> FileUtils.verifyImageFile(textFile))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void fileUrlTest() {
        // given
        var fileUrl1 = "http://file-aws.com";
        var fileUrl2 = "https://file-aws.com";
        var fileNotUrl = "file";

        // when & then
        assertThat(FileUtils.isFileUrl(fileUrl1)).isTrue();
        assertThat(FileUtils.isFileUrl(fileUrl2)).isTrue();
        assertThat(FileUtils.isFileUrl(fileNotUrl)).isFalse();
    }

}