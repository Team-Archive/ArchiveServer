package site.archive.infra.user.oauth.provider.dto;

import site.archive.api.dto.user.OAuthRegisterDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KakaoProviderRequirements {

    private final String kakaoAccessToken;

    public static KakaoProviderRequirements from(OAuthRegisterDto oAuthRegisterDto) {
        return new KakaoProviderRequirements(oAuthRegisterDto.getToken());
    }

}
