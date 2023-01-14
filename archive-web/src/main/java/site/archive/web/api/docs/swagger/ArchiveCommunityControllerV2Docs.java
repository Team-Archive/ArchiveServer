package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.ArchiveCommunityResponseDto;

import java.util.List;

public interface ArchiveCommunityControllerV2Docs {

    @Operation(summary = "아카이브 전시소통 리스트 조회")
    @GetMapping
    ResponseEntity<List<ArchiveCommunityResponseDto>> archiveCommunityView(UserInfo user,
                                                                           @ParameterObject ArchivePageable pageable);

}
