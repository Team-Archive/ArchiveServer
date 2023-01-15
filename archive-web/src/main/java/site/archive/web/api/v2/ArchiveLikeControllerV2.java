package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ArchiveLikeListResponseDto;
import site.archive.service.archive.ArchiveService;
import site.archive.service.like.LikeService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.api.docs.swagger.ArchiveLikeControllerV2Docs;

@RestController
@RequestMapping("/api/v2/archive/like")
@RequiredArgsConstructor
public class ArchiveLikeControllerV2 implements ArchiveLikeControllerV2Docs {

    private final ArchiveService archiveService;
    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<ArchiveLikeListResponseDto> archiveLikeListView(@RequestUser UserInfo userInfo) {
        var archiveIds = likeService.likeArchiveIds(userInfo.getUserId());
        var likeArchives = archiveService.getAllArchive(userInfo.getUserId(), archiveIds);
        return ResponseEntity.ok(new ArchiveLikeListResponseDto(likeArchives.size(), likeArchives));
    }

}
