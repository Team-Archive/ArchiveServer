package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.domain.user.entity.OAuthProvider;

public class AppleClient implements OAuthProviderClient {

    @Override
    public String support() {
        return OAuthProvider.APPLE.getRegistrationId();
    }

}
