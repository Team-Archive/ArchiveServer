package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.archive.common.FileUtils;
import site.archive.common.cache.CacheType.CacheInfo;
import site.archive.domain.banner.BannerType;
import site.archive.dto.v2.BannerListResponseDto;
import site.archive.service.archive.ArchiveImageService;
import site.archive.service.banner.BannerService;
import site.archive.web.api.docs.swagger.BannerControllerV2Docs;
import site.archive.web.config.security.authz.AdminChecker;
import site.archive.web.config.security.authz.annotation.RequirePermission;

import static site.archive.service.archive.ArchiveImageService.BANNER_MAIN_IMAGE_DIRECTORY;
import static site.archive.service.archive.ArchiveImageService.BANNER_SUMMARY_IMAGE_DIRECTORY;

@RestController
@RequestMapping("/api/v2/banner")
@RequiredArgsConstructor
public class BannerControllerV2 implements BannerControllerV2Docs {

    private final BannerService bannerService;
    private final ArchiveImageService imageService;

    @Cacheable(CacheInfo.BANNERS)
    @GetMapping
    public ResponseEntity<BannerListResponseDto> archiveCommunityBannerView() {
        return ResponseEntity.ok(bannerService.getAllBanner());
    }

    @CacheEvict(CacheInfo.BANNERS)
    @RequirePermission(handler = AdminChecker.class)
    @PostMapping(path = "/type/image",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createArchiveCommunityBanner(@RequestParam(name = "summaryImage") MultipartFile summaryImage,
                                                             @RequestParam(name = "mainImage") MultipartFile mainImage) {
        var summaryImageUri = imageUploadAndGetUri(BANNER_SUMMARY_IMAGE_DIRECTORY, summaryImage);
        var mainImageUri = imageUploadAndGetUri(BANNER_MAIN_IMAGE_DIRECTORY, mainImage);
        bannerService.createBanner(summaryImageUri, mainImageUri, BannerType.IMAGE);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(CacheInfo.BANNERS)
    @RequirePermission(handler = AdminChecker.class)
    @PostMapping(path = "/type/url",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createArchiveCommunityBanner(@RequestParam(name = "summaryImage") MultipartFile summaryImage,
                                                             @RequestParam String mainContentUrl) {
        var summaryImageUri = imageUploadAndGetUri(BANNER_SUMMARY_IMAGE_DIRECTORY, summaryImage);
        bannerService.createBanner(summaryImageUri, mainContentUrl, BannerType.URL);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(CacheInfo.BANNERS)
    @RequirePermission(handler = AdminChecker.class)
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<Void> deleteArchiveCommunityBanner(@PathVariable Long bannerId) {
        bannerService.deleteBanner(bannerId);
        return ResponseEntity.noContent().build();
    }

    private String imageUploadAndGetUri(String directory, MultipartFile imageFile) {
        FileUtils.verifyImageFile(imageFile);
        return imageService.upload(directory, imageFile);
    }

}


