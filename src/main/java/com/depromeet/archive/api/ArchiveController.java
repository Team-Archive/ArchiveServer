package com.depromeet.archive.api;

import com.depromeet.archive.api.dto.archive.ArchiveDto;
import com.depromeet.archive.api.dto.archive.ArchiveImageUrlResponseDto;
import com.depromeet.archive.api.dto.archive.ArchiveListDto;
import com.depromeet.archive.domain.archive.ArchiveImageService;
import com.depromeet.archive.domain.archive.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;
    private final ArchiveImageService imageService;

    @GetMapping
    public ResponseEntity<ArchiveListDto> archiveListView() {
        return ResponseEntity.ok(archiveService.getAllArchive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveDto> archiveSpecificView(@PathVariable Long id) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        archiveService.delete(id);
    }
  
    @PostMapping(path = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArchiveImageUrlResponseDto> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        imageService.verifyImageFile(imageFile);
        var imageUrl = imageService.upload(imageFile);
        return ResponseEntity.ok(new ArchiveImageUrlResponseDto(imageUrl));
    }

}
