package site.archive.service.banner

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.domain.banner.Banner
import site.archive.domain.banner.BannerRepository
import site.archive.domain.banner.BannerType
import site.archive.dto.v2.BannerListResponseDto
import site.archive.dto.v2.BannerResponseDto

@Service
@Transactional(readOnly = true)
class BannerService(val bannerRepository: BannerRepository) {

    fun getAllBanner(): BannerListResponseDto {
        val bannerResponse = bannerRepository.findAllByOrderByCreatedAtDesc()
            .map { BannerResponseDto(it.type.toString(), it.summaryImage, it.mainContent) }
            .toList()
        return BannerListResponseDto(bannerResponse)
    }

    @Transactional
    fun createBanner(summaryImageUri: String, mainContent: String, type: BannerType): BannerResponseDto {
        val banner = bannerRepository.save(Banner(summaryImageUri, mainContent, type))
        return BannerResponseDto.from(banner)
    }

    @Transactional
    fun deleteBanner(bannerId: Long) {
        bannerRepository.deleteById(bannerId)
    }

}