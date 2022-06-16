package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.dto.archive.ArchiveDto;
import site.archive.api.dto.archive.ArchiveListResponseDto;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.domain.archive.ArchiveService;
import site.archive.domain.user.info.UserInfo;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class ArchiveControllerV2 {

    private final ArchiveService archiveService;

    @Operation(summary = "특정 유저 아카이브 리스트 조회")
    @GetMapping("/other")
    public ResponseEntity<ArchiveListResponseDto> archiveListView(@RequestUser UserInfo user,
                                                                  @RequestParam Long userId) {
        return ResponseEntity.ok(archiveService.getAllArchive(user, userId));
    }

    @Operation(summary = "아카이브 상세 조회")
    @GetMapping("/{archiveId}")
    public ResponseEntity<ArchiveDto> archiveSpecificView(@RequestUser UserInfo user,
                                                          @PathVariable Long archiveId) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(user, archiveId));
    }


}
