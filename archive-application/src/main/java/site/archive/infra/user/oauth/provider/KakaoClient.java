package site.archive.infra.user.oauth.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.archive.common.exception.user.OAuthRegisterFailException;
import site.archive.domain.user.OAuthProvider;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.infra.user.oauth.provider.dto.KakaoUserInfo;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient implements OAuthProviderClient {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUrl;

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public OAuthRegisterCommandV1 getOAuthRegisterInfo(String accessToken) {
        var userEmail = getUserEmail(accessToken);
        return new OAuthRegisterCommandV1(userEmail, OAuthProvider.KAKAO);
    }

    @Override
    public String getEmail(String accessToken) {
        return getUserEmail(accessToken);
    }

    private String getUserEmail(String accessToken) {
        var entity = userInfoRequestEntity(accessToken);
        var response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, KakaoUserInfo.class);
        var kakaoUserInfo = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || kakaoUserInfo == null || kakaoUserInfo.getEmail() == null) {
            log.error("Kakao getUserEmail process - get user info error: status code {}, user info {}",
                      response.getStatusCodeValue(), userInfoUrl);
            throw new OAuthRegisterFailException(OAuthProvider.KAKAO.getRegistrationId(), "UserInfoUrl Response error");
        }

        return kakaoUserInfo.getEmail();
    }

    private HttpEntity<Object> userInfoRequestEntity(String accessToken) {
        var authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return new HttpEntity<>(authHeader);
    }

}
