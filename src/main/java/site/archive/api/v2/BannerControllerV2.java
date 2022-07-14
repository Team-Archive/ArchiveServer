package site.archive.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.v2.dto.BannerListResponseDto;
import site.archive.domain.banner.BannerService;

@RestController
@RequestMapping("/api/v2/banner")
@RequiredArgsConstructor
public class BannerControllerV2 {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<BannerListResponseDto> archiveCommunityBannerView() {
        return ResponseEntity.ok(bannerService.getAllBanner());
    }

}


