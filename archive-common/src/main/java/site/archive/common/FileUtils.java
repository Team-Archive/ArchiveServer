package site.archive.common;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class FileUtils {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    private FileUtils() {
    }

    public static void verifyImageFile(final MultipartFile imageFile) {
        if (!Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE)
                   .contains(imageFile.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
    }

    public static boolean isFileUrl(final String file) {
        return file != null && (file.startsWith(HTTP) || file.startsWith(HTTPS));
    }

}
