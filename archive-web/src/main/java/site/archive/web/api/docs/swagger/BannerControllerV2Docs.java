package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import site.archive.dto.v2.BannerListResponseDto;

public interface BannerControllerV2Docs {

    @Operation(summary = "배너 조회 [캐싱 - 1시간]")
    ResponseEntity<BannerListResponseDto> archiveCommunityBannerView();

    @Operation(summary = "배너 생성 (업로드) - 이미지 타입")
    ResponseEntity<Void> createArchiveCommunityBanner(
        @Parameter(name = "summaryImage", description = "전시소통 배너칸 노출 이미지") MultipartFile summaryImage,
        @Parameter(name = "mainImage", description = "배너 메인 이미지") MultipartFile mainImage);

    @Operation(summary = "배너 생성 (업로드) - URL 타입")
    ResponseEntity<Void> createArchiveCommunityBanner(
        @Parameter(name = "summaryImage", description = "전시소통 배너칸 노출 이미지") MultipartFile summaryImage,
        @Parameter(name = "mainContentUrl", description = "배너 연결 URL") String mainContentUrl);

    @Operation(summary = "배너 제거")
    ResponseEntity<Void> deleteArchiveCommunityBanner(@Parameter(name = "bannerId", description = "제거할 배너 Index") Long bannerId);

}


