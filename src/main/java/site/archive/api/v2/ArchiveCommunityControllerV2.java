package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.v2.dto.ArchiveCommunityResponseDto;
import site.archive.domain.archive.ArchiveCommunityService;
import site.archive.domain.archive.ArchiveCommunityTimeSortType;
import site.archive.domain.archive.entity.Emotion;

import java.util.List;

@RestController
@RequestMapping("/api/v2/archive/community")
@RequiredArgsConstructor
public class ArchiveCommunityControllerV2 {

    private final ArchiveCommunityService archiveCommunityService;

    @Operation(summary = "아카이브 전시소통 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ArchiveCommunityResponseDto>> archiveCommunityView(
        @RequestParam ArchiveCommunityTimeSortType sortType,
        @RequestParam(required = false) Emotion emotion,
        @RequestParam(required = false) Long lastSeenArchiveDateMilli,
        @RequestParam(required = false) Long lastSeenArchiveId) {
        var archiveCommunityDtos = isFirstPage(lastSeenArchiveDateMilli, lastSeenArchiveId)
                                   ? archiveCommunityService.archiveCommunityFirstPage(sortType, emotion)
                                   : archiveCommunityService.archiveCommunityNextPage(sortType,
                                                                                      emotion,
                                                                                      lastSeenArchiveDateMilli,
                                                                                      lastSeenArchiveId);
        return ResponseEntity.ok(archiveCommunityDtos);
    }

    private boolean isFirstPage(Long lastSeenArchiveDateMilli, Long lastSeenArchiveId) {
        return lastSeenArchiveDateMilli == null || lastSeenArchiveId == null;
    }

}
