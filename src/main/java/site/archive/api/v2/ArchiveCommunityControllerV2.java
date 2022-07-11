package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.v2.dto.ArchiveCommunityResponseDto;
import site.archive.domain.archive.ArchiveCommunityService;
import site.archive.domain.archive.ArchivePageable;

import java.util.List;

@RestController
@RequestMapping("/api/v2/archive/community")
@RequiredArgsConstructor
public class ArchiveCommunityControllerV2 {

    private final ArchiveCommunityService archiveCommunityService;

    @Operation(summary = "아카이브 전시소통 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ArchiveCommunityResponseDto>> archiveCommunityView(ArchivePageable archivePageable) {

        if (archivePageable.isRequestFirstPage()) {
            return ResponseEntity.ok(archiveCommunityService.getCommunityFirstPage(archivePageable));
        }
        return ResponseEntity.ok(archiveCommunityService.getCommunityNextPage(archivePageable));
    }

}
