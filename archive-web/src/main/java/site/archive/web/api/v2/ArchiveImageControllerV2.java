package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDto;
import site.archive.service.archive.ArchiveImageService;

import static site.archive.service.archive.ArchiveImageService.ARCHIVE_IMAGE_DIRECTORY;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class ArchiveImageControllerV2 {

    private final ArchiveImageService imageService;

    @Operation(summary = "이미지 업로드")
    @PostMapping(path = "/image/upload",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArchiveImageUrlResponseDto> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        imageService.verifyImageFile(imageFile);
        var imageUri = imageService.upload(ARCHIVE_IMAGE_DIRECTORY, imageFile);
        return ResponseEntity.ok(new ArchiveImageUrlResponseDto(imageUri));
    }

    @Operation(summary = "이미지 제거")
    @DeleteMapping("/image/remove")
    public ResponseEntity<Void> removeImage(@RequestParam String fileUri) {
        imageService.remove(fileUri);
        return ResponseEntity.noContent().build();
    }

}
