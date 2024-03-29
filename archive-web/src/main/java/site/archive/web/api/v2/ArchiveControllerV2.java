package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.ArchiveListResponseDtoV1;
import site.archive.dto.v2.ArchiveCountResponseDto;
import site.archive.dto.v2.ArchiveDtoV2;
import site.archive.dto.v2.MyArchiveListResponseDto;
import site.archive.service.archive.ArchiveService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.api.docs.swagger.ArchiveControllerV2Docs;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class ArchiveControllerV2 implements ArchiveControllerV2Docs {

    private final ArchiveService archiveService;

    @GetMapping
    public ResponseEntity<MyArchiveListResponseDto> archiveListView(@RequestUser UserInfo userInfo,
                                                                    ArchivePageable pageable) {
        // var archiveCount = archiveService.countArchive(userInfo);
        var myArchives = pageable.isRequestFirstPage()
                         ? archiveService.getAllArchiveFirstPage(userInfo, pageable)
                         : archiveService.getAllArchiveNextPage(userInfo, pageable);
        return ResponseEntity.ok(MyArchiveListResponseDto.from(myArchives.size(), myArchives));
    }

    @GetMapping("/other")
    public ResponseEntity<ArchiveListResponseDtoV1> archiveListView(@RequestUser UserInfo userInfo,
                                                                    @RequestParam Long userId) {
        return ResponseEntity.ok(archiveService.getAllArchive(userInfo, userId));
    }

    @GetMapping("/{archiveId}")
    public ResponseEntity<ArchiveDtoV2> archiveSpecificView(@RequestUser UserInfo userInfo,
                                                            @PathVariable Long archiveId) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(userInfo, archiveId));
    }

    @GetMapping("/count/month")
    public ResponseEntity<ArchiveCountResponseDto> countArchiveOfCurrentMonth(@RequestUser UserInfo userInfo) {
        var count = archiveService.countArchiveOfCurrentMonth(userInfo);
        return ResponseEntity.ok(new ArchiveCountResponseDto(count));
    }

    @PutMapping("/{archiveId}")
    public ResponseEntity<Void> archivePublicPrivate(@RequestUser UserInfo userInfo,
                                                     @PathVariable Long archiveId,
                                                     @RequestParam(value = "isPublic") Boolean isPublic) {
        archiveService.updateArchivePublicPrivate(userInfo, archiveId, isPublic);
        return ResponseEntity.noContent().build();
    }

}
