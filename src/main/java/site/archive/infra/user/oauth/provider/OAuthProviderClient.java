package site.archive.infra.user.oauth.provider;

import site.archive.api.command.OAuthRegisterCommand;
import site.archive.api.dto.user.OAuthRegisterRequestDto;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto);

}
