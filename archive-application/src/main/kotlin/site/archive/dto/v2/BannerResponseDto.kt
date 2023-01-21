package site.archive.dto.v2

import site.archive.domain.banner.Banner

data class BannerResponseDto(val type: String, val summaryImage: String, val mainContent: String) {

    companion object {
        @JvmStatic
        fun from(banner: Banner): BannerResponseDto {
            return BannerResponseDto(
                banner.type.toString(),
                banner.summaryImage,
                banner.mainContent
            )
        }
    }

}