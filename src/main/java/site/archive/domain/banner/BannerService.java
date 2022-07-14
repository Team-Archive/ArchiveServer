package site.archive.domain.banner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.api.v2.dto.BannerListResponseDto;
import site.archive.api.v2.dto.BannerResponseDto;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerListResponseDto getAllBanner() {
        var bannerResponse = bannerRepository.findAllByOrderByCreatedAtDesc().stream()
                                             .map(banner -> new BannerResponseDto(banner.getSummaryImage(), banner.getMainImage()))
                                             .toList();
        return new BannerListResponseDto(bannerResponse);
    }

}
