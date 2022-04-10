package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto);

}
