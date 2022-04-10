package com.depromeet.archive.infra.user.provider.dto;

import lombok.Getter;

@Getter
public class OAuthRequirement {

    private String oAuthAccessToken;

    public OAuthRequirement() {
    }

    public OAuthRequirement(String oAuthAccessToken) {
        this.oAuthAccessToken = oAuthAccessToken;
    }

}
