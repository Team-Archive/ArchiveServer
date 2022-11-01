package site.archive.infra.user.oauth.provider;

import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommandV1 getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto);

}
