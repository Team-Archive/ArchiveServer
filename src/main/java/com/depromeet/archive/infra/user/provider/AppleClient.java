package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.OAuthProvider;

public class AppleClient implements OAuthProviderClient {

    @Override
    public String support() {
        return OAuthProvider.APPLE.getRegistrationId();
    }

    @Override
    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto) {
        return null;
    }

}
