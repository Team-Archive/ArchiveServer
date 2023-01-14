package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ArchiveLikeListResponseDto;

public interface ArchiveLikeControllerV2Docs {

    @Operation(summary = "좋아요 한 Archive 조회")
    ResponseEntity<ArchiveLikeListResponseDto> archiveLikeListView(UserInfo userInfo);

}
