package com.depromeet.archive.infra.user.oauth.provider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public class KakaoUserInfo {

    private final String id;
    private final Map<String, String> properties;

    @JsonProperty(value = "kakao_account")
    private final KakaoAccount kakaoAccount;

    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @RequiredArgsConstructor
    @Getter
    public static class KakaoAccount {

        private final String email;
        private final Map<String, String> profile;

    }

}
