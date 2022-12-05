package site.archive.dto.v2;

import site.archive.domain.banner.Banner;

public record BannerResponseDto(String type, String summaryImage, String mainContent) {

    public static BannerResponseDto from(Banner banner) {
        return new BannerResponseDto(banner.getType().toString(),
                                     banner.getSummaryImage(),
                                     banner.getMainContent());
    }

}
