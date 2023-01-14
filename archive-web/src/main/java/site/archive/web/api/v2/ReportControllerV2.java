package site.archive.web.api.v2;

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
import site.archive.service.report.ReportService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.api.docs.swagger.ReportControllerV2Docs;

@RestController
@RequestMapping("/api/v2/report")
@RequiredArgsConstructor
public class ReportControllerV2 implements ReportControllerV2Docs {

    private final ReportService reportService;

    @GetMapping("/{archiveId}")
    public ResponseEntity<ReportCheckResponse> isReport(@RequestUser UserInfo userInfo,
                                                        @PathVariable Long archiveId) {
        var isReported = reportService.isReportedBy(archiveId, userInfo.getUserId());
        return ResponseEntity.ok(new ReportCheckResponse(isReported));
    }

    @PostMapping("/{archiveId}")
    public ResponseEntity<Void> report(@RequestUser UserInfo userInfo,
                                       @PathVariable Long archiveId,
                                       @RequestBody ReportRequestDto reportRequestDto) {
        reportService.reportArchive(archiveId, userInfo.getUserId(), reportRequestDto.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{archiveId}")
    public ResponseEntity<Void> reportCancel(@RequestUser UserInfo userInfo,
                                             @PathVariable Long archiveId) {
        reportService.cancelReportArchive(archiveId, userInfo.getUserId());
        return ResponseEntity.noContent().build();
    }

}
