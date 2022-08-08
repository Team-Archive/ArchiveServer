package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ArchiveCommunityResponseDto;
import site.archive.service.archive.ArchiveCommunityService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/archive/community")
@RequiredArgsConstructor
public class ArchiveCommunityControllerV2 {

    private final ArchiveCommunityService archiveCommunityService;

    @Operation(summary = "아카이브 전시소통 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ArchiveCommunityResponseDto>> archiveCommunityView(@RequestUser UserInfo user,
                                                                                  ArchivePageable pageable) {
        if (pageable.isRequestFirstPage()) {
            return ResponseEntity.ok(archiveCommunityService.getCommunityFirstPage(user.getUserId(), pageable));
        }
        return ResponseEntity.ok(archiveCommunityService.getCommunityNextPage(user.getUserId(), pageable));
    }

}
