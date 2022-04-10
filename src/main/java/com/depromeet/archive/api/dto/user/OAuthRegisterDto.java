package com.depromeet.archive.api.dto.user;

import lombok.Getter;

@Getter
public class OAuthRegisterDto {

    private String providerAccessToken;

    public OAuthRegisterDto() {
    }

    public OAuthRegisterDto(String providerAccessToken) {
        this.providerAccessToken = providerAccessToken;
    }

}
