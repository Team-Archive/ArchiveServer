package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDtoV1;

public interface ArchiveImageControllerV2Docs {

    @Operation(summary = "이미지 업로드")
    ResponseEntity<ArchiveImageUrlResponseDtoV1> uploadImage(
        @Parameter(name = "imageFile", description = "업로드 할 이미지 파일") MultipartFile imageFile);

    @Operation(summary = "이미지 제거")
    ResponseEntity<Void> removeImage(@Parameter(name = "fileUri", description = "제거할 이미지 파일 주소") String fileUri);

}
