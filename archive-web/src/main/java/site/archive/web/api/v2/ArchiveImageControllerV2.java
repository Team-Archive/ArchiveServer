package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.archive.common.FileUtils;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDtoV1;
import site.archive.service.archive.ArchiveImageService;
import site.archive.web.api.docs.swagger.ArchiveImageControllerV2Docs;

import static site.archive.service.archive.ArchiveImageService.ARCHIVE_IMAGE_DIRECTORY;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class ArchiveImageControllerV2 implements ArchiveImageControllerV2Docs {

    private final ArchiveImageService imageService;

    @PostMapping(path = "/image/upload",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArchiveImageUrlResponseDtoV1> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        FileUtils.verifyImageFile(imageFile);
        var imageUri = imageService.upload(ARCHIVE_IMAGE_DIRECTORY, imageFile);
        return ResponseEntity.ok(new ArchiveImageUrlResponseDtoV1(imageUri));
    }

    @DeleteMapping("/image/remove")
    public ResponseEntity<Void> removeImage(@RequestParam String fileUri) {
        imageService.remove(fileUri);
        return ResponseEntity.noContent().build();
    }

}
