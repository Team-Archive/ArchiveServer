package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.ArchiveListResponseDtoV1;
import site.archive.dto.v2.ArchiveCountResponseDto;
import site.archive.dto.v2.ArchiveDtoV2;
import site.archive.dto.v2.MyArchiveListResponseDto;

public interface ArchiveControllerV2Docs {

    @Operation(summary = "나의 관람 뷰 (아카이브 리스트)", description = "홈 뷰 - 아카이브 리스트 조회")
    ResponseEntity<MyArchiveListResponseDto> archiveListView(UserInfo userInfo,
                                                             @ParameterObject ArchivePageable pageable);

    @Operation(summary = "특정 유저 아카이브 리스트 조회")
    ResponseEntity<ArchiveListResponseDtoV1> archiveListView(UserInfo userInfo,
                                                             @Parameter(name = "userId", description = "조회하고자 하는 유저 Index") Long userId);

    @Operation(summary = "아카이브 상세 조회")
    ResponseEntity<ArchiveDtoV2> archiveSpecificView(UserInfo userInfo,
                                                     @Parameter(name = "archiveId", description = "archive Index (Id)") Long archiveId);

    @Operation(summary = "이번 달 아카이브 개수 조회")
    ResponseEntity<ArchiveCountResponseDto> countArchiveOfCurrentMonth(UserInfo userInfo);

    @Operation(summary = "아카이브 공개여부 설정/수정")
    ResponseEntity<Void> archivePublicPrivate(UserInfo userInfo,
                                              @Parameter(name = "archiveId", description = "archive Index (Id)") Long archiveId,
                                              Boolean isPublic);

}
