package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ReportCheckResponse;
import site.archive.dto.v2.ReportRequestDto;
import site.archive.service.archive.ArchiveService;
import site.archive.service.message.MessagingService;
import site.archive.service.report.ReportService;
import site.archive.web.api.resolver.annotation.RequestUser;

@RestController
@RequestMapping("/api/v2/report")
@RequiredArgsConstructor
public class ReportControllerV2 {

    private final ReportService reportService;
    private final ArchiveService archiveService;
    private final MessagingService messagingService;

    @Operation(summary = "신고 여부 확인")
    @GetMapping("/{archiveId}")
    public ResponseEntity<ReportCheckResponse> isReport(@PathVariable Long archiveId,
                                                        @RequestUser UserInfo userInfo) {
        var isReported = reportService.isReportedBy(archiveId, userInfo.getUserId());
        return ResponseEntity.ok(new ReportCheckResponse(isReported));
    }

    @Operation(summary = "신고하기")
    @PostMapping("/{archiveId}")
    public ResponseEntity<Void> report(@PathVariable Long archiveId,
                                       @RequestUser UserInfo userInfo,
                                       @RequestBody ReportRequestDto reportRequestDto) {
        reportService.reportArchive(archiveId, userInfo.getUserId(), reportRequestDto.getReason());
        var archive = archiveService.getOneArchiveById(archiveId);
        messagingService.sendArchiveReportMessage(userInfo.getMailAddress(), reportRequestDto.getReason(), archive);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "신고 취소하기")
    @DeleteMapping("/{archiveId}")
    public ResponseEntity<Void> reportCancel(@PathVariable Long archiveId,
                                             @RequestUser UserInfo userInfo) {
        reportService.cancelReportArchive(archiveId, userInfo.getUserId());
        return ResponseEntity.noContent().build();
    }

}
