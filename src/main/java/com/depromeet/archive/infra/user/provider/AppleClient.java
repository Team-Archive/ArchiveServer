package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.infra.user.provider.dto.OAuthRequirement;

public class AppleClient implements OAuthProviderClient {

    @Override
    public String support() {
        return OAuthProvider.APPLE.getRegistrationId();
    }

    @Override
    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRequirement oAuthRequirement) {
        return null;
    }

}
