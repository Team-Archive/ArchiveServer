package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.exception.user.OAuthRegisterFailException;
import com.depromeet.archive.infra.user.provider.dto.KakaoUserInfo;
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

    public String getUserEmail(String accessToken) {
        var authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        var entity = new HttpEntity<>(authHeader);

        var response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, KakaoUserInfo.class);
        var kakaoUserInfo = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || kakaoUserInfo == null || kakaoUserInfo.getEmail() == null) {
            log.error("Kakao register process - get user info error: status code {}, user info {}",
                    response.getStatusCodeValue(), userInfoUrl);
            throw new OAuthRegisterFailException(OAuthProvider.KAKAO, "UserInfoUrl Response error");
        }

        return kakaoUserInfo.getEmail();
    }

    @Override
    public String support() {
        return OAuthProvider.KAKAO.getRegistrationId();
    }

}
