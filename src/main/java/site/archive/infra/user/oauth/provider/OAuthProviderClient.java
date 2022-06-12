package site.archive.infra.user.oauth.provider;

import site.archive.api.dto.user.OAuthRegisterRequestDto;
import site.archive.domain.user.command.OAuthRegisterCommand;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto);

}
