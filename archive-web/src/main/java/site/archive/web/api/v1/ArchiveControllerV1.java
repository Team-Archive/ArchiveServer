package site.archive.web.api.v1;

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
import site.archive.common.FileUtils;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.ArchiveCountResponseDtoV1;
import site.archive.dto.v1.archive.ArchiveDtoV1;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDtoV1;
import site.archive.dto.v1.archive.ArchiveListResponseDtoV1;
import site.archive.service.archive.ArchiveImageService;
import site.archive.service.archive.ArchiveService;
import site.archive.web.api.docs.swagger.ArchiveControllerV1Docs;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.config.security.authz.AdminOrAuthorChecker;
import site.archive.web.config.security.authz.annotation.RequirePermission;

import static site.archive.service.archive.ArchiveImageService.ARCHIVE_IMAGE_DIRECTORY;

@RestController
@RequestMapping("/api/v1/archive")
@RequiredArgsConstructor
public class ArchiveControllerV1 implements ArchiveControllerV1Docs {

    private final ArchiveService archiveService;
    private final ArchiveImageService imageService;

    @GetMapping
    public ResponseEntity<ArchiveListResponseDtoV1> archiveListView(@RequestUser UserInfo user) {
        return ResponseEntity.ok(archiveService.getAllArchive(user));
    }

    @RequirePermission(handler = AdminOrAuthorChecker.class, id = "id")
    @GetMapping("/{id}")
    public ResponseEntity<ArchiveDtoV1> archiveSpecificView(@PathVariable Long id) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(id));
    }

    @RequirePermission(handler = AdminOrAuthorChecker.class, id = "id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        archiveService.delete(id);
    }

    @Deprecated
    @PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArchiveImageUrlResponseDtoV1> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        FileUtils.verifyImageFile(imageFile);
        var imageUri = imageService.upload(ARCHIVE_IMAGE_DIRECTORY, imageFile);
        return ResponseEntity.ok(new ArchiveImageUrlResponseDtoV1(imageUri));
    }

    @PostMapping
    public ResponseEntity<Object> addArchive(@RequestUser UserInfo user, @RequestBody ArchiveDtoV1 archiveDtoV1) {
        archiveService.save(archiveDtoV1, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/count")
    public ResponseEntity<ArchiveCountResponseDtoV1> countArchive(@RequestUser UserInfo user) {
        var archiveCountDto = new ArchiveCountResponseDtoV1(archiveService.countArchive(user));
        return ResponseEntity.ok(archiveCountDto);
    }

}
