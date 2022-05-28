package site.archive.infra.user.oauth.provider.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.api.dto.user.OAuthRegisterDto;

@RequiredArgsConstructor
@Getter
public class KakaoProviderRequirements {

    private final String kakaoAccessToken;

    public static KakaoProviderRequirements from(OAuthRegisterDto oAuthRegisterDto) {
        return new KakaoProviderRequirements(oAuthRegisterDto.getToken());
    }

}
