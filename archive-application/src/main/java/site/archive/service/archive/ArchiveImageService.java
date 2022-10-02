package site.archive.service.archive;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public interface ArchiveImageService {

    String ARCHIVE_IMAGE_DIRECTORY = "images/";
    String USER_PROFILE_IMAGE_DIRECTORY = "user-profile/";

    default void verifyImageFile(final MultipartFile imageFile) {
        if (!Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE)
                   .contains(imageFile.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
    }

    String upload(String directory, MultipartFile imageFile);

    void remove(String fileUri);

    String update(String directory, String outdatedFileName, MultipartFile imageFile);

}
