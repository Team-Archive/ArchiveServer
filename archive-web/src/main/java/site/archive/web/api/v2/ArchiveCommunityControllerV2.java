package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ArchiveCommunityResponseDto;
import site.archive.service.archive.ArchiveCommunityService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.api.docs.swagger.ArchiveCommunityControllerV2Docs;

import java.util.List;

@RestController
@RequestMapping("/api/v2/archive/community")
@RequiredArgsConstructor
public class ArchiveCommunityControllerV2 implements ArchiveCommunityControllerV2Docs {

    private final ArchiveCommunityService archiveCommunityService;

    @GetMapping
    public ResponseEntity<List<ArchiveCommunityResponseDto>> archiveCommunityView(@RequestUser UserInfo user,
                                                                                  ArchivePageable pageable) {
        if (pageable.isRequestFirstPage()) {
            return ResponseEntity.ok(archiveCommunityService.getCommunityFirstPage(user.getUserId(), pageable));
        }
        return ResponseEntity.ok(archiveCommunityService.getCommunityNextPage(user.getUserId(), pageable));
    }

}
