package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.api.v1.dto.archive.ArchiveDto;
import site.archive.api.v1.dto.archive.ArchiveListResponseDto;
import site.archive.api.v2.dto.ArchiveCountResponseDto;
import site.archive.api.v2.dto.MyArchiveListResponseDto;
import site.archive.domain.archive.ArchivePageable;
import site.archive.domain.archive.ArchiveService;
import site.archive.domain.user.info.UserInfo;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class ArchiveControllerV2 {

    private final ArchiveService archiveService;

    @Operation(summary = "나의 관람 뷰 (아카이브 리스트)", description = "홈 뷰 - 아카이브 리스트 조회")
    @GetMapping
    public ResponseEntity<MyArchiveListResponseDto> archiveListView(@RequestUser UserInfo userInfo,
                                                                    ArchivePageable pageable) {
        var archiveCount = archiveService.countArchive(userInfo);
        var myArchives = pageable.isRequestFirstPage()
                         ? archiveService.getAllArchiveFirstPage(userInfo, pageable)
                         : archiveService.getAllArchiveNextPage(userInfo, pageable);
        return ResponseEntity.ok(MyArchiveListResponseDto.from(archiveCount, myArchives));
    }

    @Operation(summary = "특정 유저 아카이브 리스트 조회")
    @GetMapping("/other")
    public ResponseEntity<ArchiveListResponseDto> archiveListView(@RequestUser UserInfo userInfo,
                                                                  @RequestParam Long userId) {
        return ResponseEntity.ok(archiveService.getAllArchive(userInfo, userId));
    }

    @Operation(summary = "아카이브 상세 조회")
    @GetMapping("/{archiveId}")
    public ResponseEntity<ArchiveDto> archiveSpecificView(@RequestUser UserInfo userInfo,
                                                          @PathVariable Long archiveId) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(userInfo, archiveId));
    }

    @Operation(summary = "이번 달 아카이브 개수 조회")
    @GetMapping("/count/month")
    public ResponseEntity<ArchiveCountResponseDto> countArchiveOfCurrentMonth(@RequestUser UserInfo userInfo) {
        var count = archiveService.countArchiveOfCurrentMonth(userInfo);
        return ResponseEntity.ok(new ArchiveCountResponseDto(count));
    }

}
