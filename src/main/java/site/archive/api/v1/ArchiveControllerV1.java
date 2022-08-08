package site.archive.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.config.security.authz.ArchiveAdminOrAuthorChecker;
import site.archive.config.security.authz.annotation.RequirePermission;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.ArchiveCountResponseDto;
import site.archive.dto.v1.archive.ArchiveDto;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDto;
import site.archive.dto.v1.archive.ArchiveListResponseDto;
import site.archive.service.archive.ArchiveImageService;
import site.archive.service.archive.ArchiveService;

@RestController
@RequestMapping("/api/v1/archive")
@RequiredArgsConstructor
public class ArchiveControllerV1 {

    private final ArchiveService archiveService;
    private final ArchiveImageService imageService;

    @Operation(summary = "아카이브 리스트 조회", description = "홈 뷰 - 아카이브 리스트 조회")
    @GetMapping
    public ResponseEntity<ArchiveListResponseDto> archiveListView(@RequestUser UserInfo user) {
        return ResponseEntity.ok(archiveService.getAllArchive(user));
    }

    @RequirePermission(handler = ArchiveAdminOrAuthorChecker.class, id = "id")
    @Operation(summary = "아카이브 상세 조회", description = "상세 뷰 - 아카이브 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ArchiveDto> archiveSpecificView(@PathVariable Long id) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(id));
    }

    @RequirePermission(handler = ArchiveAdminOrAuthorChecker.class, id = "id")
    @Operation(summary = "아카이브 삭제", description = "아카이브 제거 - 실제 제거 X")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        archiveService.delete(id);
    }

    @Deprecated
    @Operation(summary = "이미지 업로드")
    @PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArchiveImageUrlResponseDto> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        imageService.verifyImageFile(imageFile);
        var imageUrl = imageService.upload(imageFile);
        return ResponseEntity.ok(new ArchiveImageUrlResponseDto(imageUrl));
    }

    @Operation(summary = "아키이브 추가")
    @PostMapping
    public ResponseEntity<Object> addArchive(@RequestUser UserInfo user, @RequestBody ArchiveDto archiveDto) {
        archiveService.save(archiveDto, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "아카이브 개수 조회")
    @GetMapping("/count")
    public ResponseEntity<ArchiveCountResponseDto> countArchive(@RequestUser UserInfo user) {
        var archiveCountDto = new ArchiveCountResponseDto(archiveService.countArchive(user));
        return ResponseEntity.ok(archiveCountDto);
    }

}
