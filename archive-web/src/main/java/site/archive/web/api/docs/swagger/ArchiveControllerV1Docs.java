package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.ArchiveCountResponseDtoV1;
import site.archive.dto.v1.archive.ArchiveDtoV1;
import site.archive.dto.v1.archive.ArchiveImageUrlResponseDtoV1;
import site.archive.dto.v1.archive.ArchiveListResponseDtoV1;

public interface ArchiveControllerV1Docs {

    @Operation(summary = "아카이브 리스트 조회", description = "홈 뷰 - 아카이브 리스트 조회")
    ResponseEntity<ArchiveListResponseDtoV1> archiveListView(UserInfo user);

    @Operation(summary = "아카이브 상세 조회", description = "상세 뷰 - 아카이브 상세 조회")
    ResponseEntity<ArchiveDtoV1> archiveSpecificView(@Parameter(name = "id", description = "상세 조회할 archive index (id)") Long id);

    @Operation(summary = "아카이브 삭제", description = "아카이브 제거 - 실제 제거 X")
    void delete(@Parameter(name = "id", description = "제거할 archive index (id)") Long id);

    @Operation(summary = "[Deprecated -> /api/v2/user/profile/image/upload] 이미지 업로드")
    ResponseEntity<ArchiveImageUrlResponseDtoV1> uploadImage(
        @Parameter(name = "image", description = "업로드할 이미지 파일") MultipartFile imageFile);

    @Operation(summary = "아키이브 추가")
    ResponseEntity<Object> addArchive(UserInfo user, ArchiveDtoV1 archiveDtoV1);

    @Operation(summary = "아카이브 개수 조회")
    ResponseEntity<ArchiveCountResponseDtoV1> countArchive(UserInfo user);

}
