package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ReportCheckResponse;
import site.archive.dto.v2.ReportRequestDto;

public interface ReportControllerV2Docs {

    @Operation(summary = "신고 여부 확인")
    ResponseEntity<ReportCheckResponse> isReport(UserInfo userInfo,
                                                 @Parameter(name = "archiveId", description = "신고여부 확인할 archive Index") Long archiveId);

    @Operation(summary = "신고하기")
    ResponseEntity<Void> report(UserInfo userInfo,
                                @Parameter(name = "archiveId", description = "신고할 archive Index") Long archiveId,
                                ReportRequestDto reportRequestDto);

    @Operation(summary = "신고 취소하기")
    ResponseEntity<Void> reportCancel(UserInfo userInfo,
                                      @Parameter(name = "archiveId", description = "신고 취소할 archive Index") Long archiveId);

}
