package site.archive.service.archive;

import org.springframework.web.multipart.MultipartFile;

public interface ArchiveImageService {

    String ARCHIVE_IMAGE_DIRECTORY = "images/";
    String USER_PROFILE_IMAGE_DIRECTORY = "user-profile/";
    String BANNER_MAIN_IMAGE_DIRECTORY = "banner/main/";
    String BANNER_SUMMARY_IMAGE_DIRECTORY = "banner/summary/";

    String upload(String directory, MultipartFile imageFile);

    void remove(String fileUri);

    String update(String directory, String outdatedFileName, MultipartFile imageFile);

}
