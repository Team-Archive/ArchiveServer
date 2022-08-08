package site.archive.infra.user.oauth.provider.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;

@RequiredArgsConstructor
@Getter
public class KakaoProviderRequirements {

    private final String kakaoAccessToken;

    public static KakaoProviderRequirements from(OAuthRegisterRequestDto oAuthRegisterRequestDto) {
        return new KakaoProviderRequirements(oAuthRegisterRequestDto.getToken());
    }

}
