package site.archive.infra.user.oauth.provider;

import site.archive.api.dto.user.OAuthRegisterDto;
import site.archive.domain.user.command.OAuthRegisterCommand;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto);

}
