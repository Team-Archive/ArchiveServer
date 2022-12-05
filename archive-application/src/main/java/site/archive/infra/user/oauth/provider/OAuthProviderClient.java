package site.archive.infra.user.oauth.provider;

import site.archive.domain.user.OAuthProvider;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;

public interface OAuthProviderClient {

    OAuthProvider getProvider();

    OAuthRegisterCommandV1 getOAuthRegisterInfo(String accessToken);

    String getEmail(String accessToken);

}
