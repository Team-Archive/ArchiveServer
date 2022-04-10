package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.infra.user.provider.dto.OAuthRequirement;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRequirement oAuthRequirement);

}
