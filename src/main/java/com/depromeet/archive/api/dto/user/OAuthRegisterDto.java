package com.depromeet.archive.api.dto.user;

import lombok.Getter;

@Getter
public class OAuthRegisterDto {

    private String oAuthAccessToken;

    public OAuthRegisterDto() {
    }

    public OAuthRegisterDto(String oAuthAccessToken) {
        this.oAuthAccessToken = oAuthAccessToken;
    }

}
