package site.archive.service.banner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.domain.banner.BannerRepository;
import site.archive.dto.v2.BannerListResponseDto;
import site.archive.dto.v2.BannerResponseDto;

@Service
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

}
