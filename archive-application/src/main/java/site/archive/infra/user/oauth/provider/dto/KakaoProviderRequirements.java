package site.archive.infra.user.oauth.provider.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;

@RequiredArgsConstructor
@Getter
public class KakaoProviderRequirements {

    private final String kakaoAccessToken;

    public static KakaoProviderRequirements from(OAuthRegisterRequestDtoV1 oAuthRegisterRequestDtoV1) {
        return new KakaoProviderRequirements(oAuthRegisterRequestDtoV1.getToken());
    }

}
