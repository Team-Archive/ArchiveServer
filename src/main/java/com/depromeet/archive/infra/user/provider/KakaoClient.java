package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.exception.user.OAuthRegisterFailException;
import com.depromeet.archive.infra.user.provider.dto.KakaoUserInfo;
import com.depromeet.archive.infra.user.provider.dto.OAuthRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient implements OAuthProviderClient {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUrl;


    @Override
    public String support() {
        return OAuthProvider.KAKAO.getRegistrationId();
    }

    @Override
    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRequirement oAuthRequirement) {
        var userEmail = getUserEmail(oAuthRequirement.getOAuthAccessToken());
        return new OAuthRegisterCommand(userEmail, OAuthProvider.KAKAO);
    }

    public String getUserEmail(String accessToken) {
        var entity = userInfoRequestEntity(accessToken);
        var response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, KakaoUserInfo.class);
        var kakaoUserInfo = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || kakaoUserInfo == null || kakaoUserInfo.getEmail() == null) {
            log.error("Kakao getUserEmail process - get user info error: status code {}, user info {}",
                    response.getStatusCodeValue(), userInfoUrl);
            throw new OAuthRegisterFailException(OAuthProvider.KAKAO, "UserInfoUrl Response error");
        }

        return kakaoUserInfo.getEmail();
    }

    private HttpEntity<Object> userInfoRequestEntity(String accessToken) {
        var authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return new HttpEntity<>(authHeader);
    }

}
