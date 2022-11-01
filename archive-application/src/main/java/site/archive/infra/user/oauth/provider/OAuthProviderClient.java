package site.archive.infra.user.oauth.provider;

import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;

public interface OAuthProviderClient {

    String support();

    OAuthRegisterCommandV1 getOAuthRegisterInfo(OAuthRegisterRequestDtoV1 oAuthRegisterRequestDtoV1);

}
