package site.archive.service.banner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.domain.banner.Banner;
import site.archive.domain.banner.BannerRepository;
import site.archive.domain.banner.BannerType;
import site.archive.dto.v2.BannerListResponseDto;
import site.archive.dto.v2.BannerResponseDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerListResponseDto getAllBanner() {
        var bannerResponse = bannerRepository.findAllByOrderByCreatedAtDesc().stream()
                                             .map(banner -> new BannerResponseDto(banner.getType().toString(),
                                                                                  banner.getSummaryImage(),
                                                                                  banner.getMainContent()))
                                             .toList();
        return new BannerListResponseDto(bannerResponse);
    }

    @Transactional
    public BannerResponseDto createBanner(String summaryImageUri, String mainContent, BannerType type) {
        var banner = bannerRepository.save(new Banner(summaryImageUri, mainContent, type));
        return BannerResponseDto.from(banner);
    }

}
