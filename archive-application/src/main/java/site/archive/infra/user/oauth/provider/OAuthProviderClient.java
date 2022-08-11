package site.archive.infra.user.oauth.provider;

import site.archive.dto.v1.auth.OAuthRegisterCommand;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto);

}
